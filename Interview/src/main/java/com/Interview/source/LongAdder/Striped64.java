package com.Interview.source.LongAdder;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleBinaryOperator;
import java.util.function.LongBinaryOperator;

abstract class Striped64 extends Number {
    /*
     * This class maintains a lazily-initialized table of atomically
     * updated variables, plus an extra "base" field. The table size
     * is a power of two. Indexing uses masked per-thread hash codes.
     * Nearly all declarations in this class are package-private,
     * accessed directly by subclasses.
     *
     * Table entries are of class Cell; a variant of AtomicLong padded
     * (via @sun.misc.Contended) to reduce cache contention. Padding
     * is overkill for most Atomics because they are usually
     * irregularly scattered in memory and thus don't interfere much
     * with each other. But Atomic objects residing in arrays will
     * tend to be placed adjacent to each other, and so will most
     * often share cache lines (with a huge negative performance
     * impact) without this precaution.
     *
     * In part because Cells are relatively large, we avoid creating
     * them until they are needed.  When there is no contention, all
     * updates are made to the base field.  Upon first contention (a
     * failed CAS on base update), the table is initialized to size 2.
     * The table size is doubled upon further contention until
     * reaching the nearest power of two greater than or equal to the
     * number of CPUS. Table slots remain empty (null) until they are
     * needed.
     *
     * A single spinlock ("cellsBusy") is used for initializing and
     * resizing the table, as well as populating slots with new Cells.
     * There is no need for a blocking lock; when the lock is not
     * available, threads try other slots (or the base).  During these
     * retries, there is increased contention and reduced locality,
     * which is still better than alternatives.
     *
     * The Thread probe fields maintained via ThreadLocalRandom serve
     * as per-thread hash codes. We let them remain uninitialized as
     * zero (if they come in this way) until they contend at slot
     * 0. They are then initialized to values that typically do not
     * often conflict with others.  Contention and/or table collisions
     * are indicated by failed CASes when performing an update
     * operation. Upon a collision, if the table size is less than
     * the capacity, it is doubled in size unless some other thread
     * holds the lock. If a hashed slot is empty, and lock is
     * available, a new Cell is created. Otherwise, if the slot
     * exists, a CAS is tried.  Retries proceed by "double hashing",
     * using a secondary hash (Marsaglia XorShift) to try to find a
     * free slot.
     *
     * The table size is capped because, when there are more threads
     * than CPUs, supposing that each thread were bound to a CPU,
     * there would exist a perfect hash function mapping threads to
     * slots that eliminates collisions. When we reach capacity, we
     * search for this mapping by randomly varying the hash codes of
     * colliding threads.  Because search is random, and collisions
     * only become known via CAS failures, convergence can be slow,
     * and because threads are typically not bound to CPUS forever,
     * may not occur at all. However, despite these limitations,
     * observed contention rates are typically low in these cases.
     *
     * It is possible for a Cell to become unused when threads that
     * once hashed to it terminate, as well as in the case where
     * doubling the table causes no thread to hash to it under
     * expanded mask.  We do not try to detect or remove such cells,
     * under the assumption that for long-running instances, observed
     * contention levels will recur, so the cells will eventually be
     * needed again; and for short-lived ones, it does not matter.
     */

    /**
     * Padded variant of AtomicLong supporting only raw accesses plus CAS.
     *
     * JVM intrinsics note: It would be possible to use a release-only
     * form of CAS here, if it were provided.
     */
    @sun.misc.Contended static final class Cell {
        volatile long value;
        Cell(long x) { value = x; }
        final boolean cas(long cmp, long val) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, cmp, val);
        }

        // Unsafe mechanics
        private static final sun.misc.Unsafe UNSAFE;
        private static final long valueOffset;
        static {
            try {
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                Class<?> ak = Striped64.Cell.class;
                valueOffset = UNSAFE.objectFieldOffset
                        (ak.getDeclaredField("value"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    /** Number of CPUS, to place bound on table size */
    //当前计算器CPU数量
    static final int NCPU = Runtime.getRuntime().availableProcessors();

    /**
     * Table of cells. When non-null, size is a power of 2.
     * 单元格数组
     */
    transient volatile Cell[] cells;

    /**
     * Base value, used mainly when there is no contention, but also as
     * a fallback during table initialization races. Updated via CAS.
     * 未发生竞争时,数据会累加到base上,当cells扩容时会将数据写到cells中
     */
    transient volatile long base;

    /**
     * Spinlock (locked via CAS) used when resizing and/or creating Cells.
     * 初始化cells,或者扩容cells都需要获取锁,0表示无锁 ,1表示其他线程已经持有锁
     */
    transient volatile int cellsBusy;

    /**
     * Package-private default constructor
     */
    Striped64() {
    }

    /**
     * CASes the base field.
     */
    final boolean casBase(long cmp, long val) {
        return UNSAFE.compareAndSwapLong(this, BASE, cmp, val);
    }

    /**
     * CASes the cellsBusy field from 0 to 1 to acquire lock.
     * cas方式获取锁,期望0,修改为1
     */
    final boolean casCellsBusy() {
        return UNSAFE.compareAndSwapInt(this, CELLSBUSY, 0, 1);
    }

    /**
     * Returns the probe value for the current thread.
     * Duplicated from ThreadLocalRandom because of packaging restrictions.
     * 获取当前线程的hash值
     */
    static final int getProbe() {
        return UNSAFE.getInt(Thread.currentThread(), PROBE);
    }

    /**
     * Pseudo-randomly advances and records the given probe value for the
     * given thread.
     * Duplicated from ThreadLocalRandom because of packaging restrictions.
     * 重置当前线程的hash值
     */
    static final int advanceProbe(int probe) {
        probe ^= probe << 13;   // xorshift
        probe ^= probe >>> 17;
        probe ^= probe << 5;
        UNSAFE.putInt(Thread.currentThread(), PROBE, probe);
        return probe;
    }

    /**
     * Handles cases of updates involving initialization, resizing,
     * creating new Cells, and/or contention. See above for
     * explanation. This method suffers the usual non-modularity
     * problems of optimistic retry code, relying on rechecked sets of
     * reads.
     *
     * @param x the value 增量
     * @param fn the update function, or null for add (this convention
     * avoids the need for an extra field or function in LongAdder). 操作算法的接口,这里为null
     * @param wasUncontended false if CAS failed before call 只有当前线程修改单元格竞争失败为false
     */
    final void longAccumulate(long x, LongBinaryOperator fn,
                              boolean wasUncontended) {
        //哪些情况会进来?
        //true->cell数组未初始化
        //true->当前线程cell为空,需要创建
        //true->当前线程命中的单元格为空
        //true->当前线程修改对应cell失败,进行扩容或者重试
        int h;//当前线程的hash值
        /**
         * true->当前线程还未分配hash值
         * false->当前线程已经分配了
         */
        if ((h = getProbe()) == 0) {
            ThreadLocalRandom.current(); // force initialization 获取当前线程的随机值
            //分配hash值给h
            h = getProbe();
            //强制设置为true ,因为当前getProbe得到的是0,与运算肯定是cells[0]的位置,不把它当作一次真正的竞争,重新分配后修改值
            wasUncontended = true;
        }
        /**
         * true->可能会扩容
         * false->肯定不会扩容
         */
        boolean collide = false;                // True if last slot nonempty
        //自旋
        for (;;) {
            //as 表示cell数组
            //a 表示当前线程命中的cell
            //n 表示数组长度
            //v 表示期望值
            Cell[] as; Cell a; int n; long v;
            /**
             * CASE1:
             *  条件1:cells数组赋值给as
             *      true->cells数组不为空
             *      false->cells数组为空
             *  条件1:cells数组赋值给as
             *        true->cells长度大于0
             *        false-> cells数组小于0
             */
            if ((as = cells) != null && (n = as.length) > 0) {
                //true->cells数组不为空,cas有竞争才会写到cell中
                //true->cells长度大于0
                /**
                 * CASE1.1:
                 *      true->寻址,对应地址value为空
                 *      false->寻址,对应地址value不为空
                 */
                if ((a = as[(n - 1) & h]) == null) {
                    /**
                     * true->表示当前是无锁状态
                     * false->当前锁锁被占用
                     */
                    if (cellsBusy == 0) {       // Try to attach new Cell
                        Cell r = new Cell(x);   // Optimistically create
                        /**
                         * 条件1:
                         *      true->表示当前是无锁状态
                         *      false->当前锁锁被占用
                         * 条件2:
                         *      true->抢占锁成功
                         *      false->抢占锁失败
                         */
                        if (cellsBusy == 0 && casCellsBusy()) {
                            //创建标记
                            boolean created = false;
                            try {               // Recheck under lock
                                //rs表示当前cells引用
                                //m表示cells长度
                                //表示当前线程命中下标
                                Cell[] rs; int m, j;
                                /**
                                 * 条件1,2没意义,上面已经判断过.
                                 * 条件3:再次判断该下标是否为空,否则其他线程可能修改过cell,会丢失数据
                                 *      true->直接复制,修改标记跳出循环
                                 *      false->继续下次循环
                                 */
                                if ((rs = cells) != null &&
                                        (m = rs.length) > 0 &&
                                        rs[j = (m - 1) & h] == null) {
                                    rs[j] = r;
                                    created = true;
                                }
                            } finally {
                                cellsBusy = 0;
                            }
                            if (created)
                                break;
                            continue;           // Slot is now non-empty
                        }
                    }
                    //当前线程还未修改,不会扩容
                    collide = false;
                }
                /**
                 * CASE1.2:如果没有锁竞争,则修改为有竞争
                 * wasUncontended:只有当前线程修改单元格竞争失败才会false
                 */
                else if (!wasUncontended)       // CAS already known to fail
                    wasUncontended = true;      // Continue after rehash
                /**
                 * CASE1.3:
                 *      true-> 当前线程修改cell值成功
                 *      false-> 表示rehash后命中的新的cell也有竞争,当前修改cell值失败
                 */
                else if (a.cas(v = a.value, ((fn == null) ? v + x :
                        fn.applyAsLong(v, x))))
                    break;
                /**
                 * CASE1.4: 1.3重试失败后
                 * 条件1:
                 *      true->cells数组长度大于等于cpu核数:不会扩容 rehash后重试
                 *      false->cells数组长度小于cpu核数
                 * 条件2:
                 *      true-> cells不等于as:不会扩容 rehash后重试
                 *      false->cells等于as
                 */
                else if (n >= NCPU || cells != as)
                    collide = false;            // At max size or stale
                /**
                 * CASE1.5:
                 *      true->可能会扩容 rehash后重试
                 *      false->不可能扩容
                 */
                else if (!collide)
                    collide = true;
                /**
                 * CASE1.6: 真正扩容的逻辑
                 * 条件1:
                 *      true->锁空闲
                 *      false->锁被占用
                 * 条件2:
                 *      true->抢占锁成功
                 *      false->抢占锁失败
                 *
                 */
                else if (cellsBusy == 0 && casCellsBusy()) {
                    try {
                        //true->cells等于as,则进行扩容
                        //false->表示cells数组被扩容过了,重新循环进行操作
                        if (cells == as) {      // Expand table unless stale
                            //扩容一倍,重新赋值
                            Cell[] rs = new Cell[n << 1];
                            for (int i = 0; i < n; ++i)
                                rs[i] = as[i];
                            cells = rs;
                        }
                    } finally {
                        //释放锁
                        cellsBusy = 0;
                    }
                    //不会进行扩容意向了
                    collide = false;
                    continue;                   // Retry with expanded table
                }
                //重置当前线程hash值
                h = advanceProbe(h);
            }
            /**
             * CASE2: 前置条件 as未初始化为空
             * 条件1: true->当前cell锁空闲
             *        false->当前cell被其他线程持有
             * 条件2: true-> cells等于as (有可能在运行过程中被其他线程修改,cells可能被赋值了)
             *        false->cells不等于as
             * 条件3: true-> 当前线程获取cell锁成功 CELLSBUSY=0,修改为1
             *        false->当前线程获取cell锁失败 CELLSBUSY=1
             */
            else if (cellsBusy == 0 && cells == as && casCellsBusy()) {
                boolean init = false;
                try {                           // Initialize table
                    /**
                     * 防止获取锁之前被其他线程修改,所以重新判断,否则会丢失数据
                     */
                    if (cells == as) {
                        Cell[] rs = new Cell[2];
                        rs[h & 1] = new Cell(x);
                        cells = rs;
                        init = true;
                    }
                } finally {
                    cellsBusy = 0;
                }
                if (init)
                    break;
            }
            /**
             * CASE3: 前置条件->1:as为空
             *                  2:as!=nullcells
             *                  3:CELLSBUSY=1
             * 条件:
             *      true->当前线程将数据累到base,退出循环
             *      false->当前线程将数据累到base失败,重新循环
             */
            else if (casBase(v = base, ((fn == null) ? v + x :
                    fn.applyAsLong(v, x))))
                break;                          // Fall back on using base
        }
    }

    /**
     * Same as longAccumulate, but injecting long/double conversions
     * in too many places to sensibly merge with long version, given
     * the low-overhead requirements of this class. So must instead be
     * maintained by copy/paste/adapt.
     */
    final void doubleAccumulate(double x, DoubleBinaryOperator fn,
                                boolean wasUncontended) {
        int h;
        if ((h = getProbe()) == 0) {
            ThreadLocalRandom.current(); // force initialization
            h = getProbe();
            wasUncontended = true;
        }
        boolean collide = false;                // True if last slot nonempty
        for (;;) {
            Cell[] as; Cell a; int n; long v;
            if ((as = cells) != null && (n = as.length) > 0) {
                if ((a = as[(n - 1) & h]) == null) {
                    if (cellsBusy == 0) {       // Try to attach new Cell
                        Cell r = new Cell(Double.doubleToRawLongBits(x));
                        if (cellsBusy == 0 && casCellsBusy()) {
                            boolean created = false;
                            try {               // Recheck under lock
                                Cell[] rs; int m, j;
                                if ((rs = cells) != null &&
                                        (m = rs.length) > 0 &&
                                        rs[j = (m - 1) & h] == null) {
                                    rs[j] = r;
                                    created = true;
                                }
                            } finally {
                                cellsBusy = 0;
                            }
                            if (created)
                                break;
                            continue;           // Slot is now non-empty
                        }
                    }
                    collide = false;
                }
                else if (!wasUncontended)       // CAS already known to fail
                    wasUncontended = true;      // Continue after rehash
                else if (a.cas(v = a.value,
                        ((fn == null) ?
                                Double.doubleToRawLongBits
                                        (Double.longBitsToDouble(v) + x) :
                                Double.doubleToRawLongBits
                                        (fn.applyAsDouble
                                                (Double.longBitsToDouble(v), x)))))
                    break;
                else if (n >= NCPU || cells != as)
                    collide = false;            // At max size or stale
                else if (!collide)
                    collide = true;
                else if (cellsBusy == 0 && casCellsBusy()) {
                    try {
                        if (cells == as) {      // Expand table unless stale
                            Cell[] rs = new Cell[n << 1];
                            for (int i = 0; i < n; ++i)
                                rs[i] = as[i];
                            cells = rs;
                        }
                    } finally {
                        cellsBusy = 0;
                    }
                    collide = false;
                    continue;                   // Retry with expanded table
                }
                h = advanceProbe(h);
            }
            else if (cellsBusy == 0 && cells == as && casCellsBusy()) {
                boolean init = false;
                try {                           // Initialize table
                    if (cells == as) {
                        Cell[] rs = new Cell[2];
                        rs[h & 1] = new Cell(Double.doubleToRawLongBits(x));
                        cells = rs;
                        init = true;
                    }
                } finally {
                    cellsBusy = 0;
                }
                if (init)
                    break;
            }
            else if (casBase(v = base,
                    ((fn == null) ?
                            Double.doubleToRawLongBits
                                    (Double.longBitsToDouble(v) + x) :
                            Double.doubleToRawLongBits
                                    (fn.applyAsDouble
                                            (Double.longBitsToDouble(v), x)))))
                break;                          // Fall back on using base
        }
    }

    // Unsafe mechanics
    private static final sun.misc.Unsafe UNSAFE;
    private static final long BASE;
    private static final long CELLSBUSY;
    private static final long PROBE;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> sk = Striped64.class;
            BASE = UNSAFE.objectFieldOffset
                    (sk.getDeclaredField("base"));
            CELLSBUSY = UNSAFE.objectFieldOffset
                    (sk.getDeclaredField("cellsBusy"));
            Class<?> tk = Thread.class;
            PROBE = UNSAFE.objectFieldOffset
                    (tk.getDeclaredField("threadLocalRandomProbe"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

}
