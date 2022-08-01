package com.Interview;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

public class LongAdderDemo {
    private static final long PROBE;
    static {
        try {
            Class<?> tk = Thread.class;
            PROBE = createUnsafe().objectFieldOffset
                    (tk.getDeclaredField("threadLocalRandomProbe"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
    public static void main(String[] args) {
//        ThreadLocalRandom.current();-1640531527
        int anInt = getProbe();
        System.out.println(anInt);
        ThreadLocalRandom.current();
        int anInt1 = getProbe();
        System.out.println(anInt1);
    }
    static final int getProbe() {
        return createUnsafe().getInt(Thread.currentThread(), PROBE);
    }
    public static Unsafe createUnsafe() {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);
            return unsafe;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
