package practice.threadLocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ThreadLocalDemo {
    public static void main(String[] args) throws InterruptedException {
        House house = new House();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5) + 1;
                try {
                    for (int j = 0; j < size; j++) {
                        house.setSaleCountByThreadLocal();
                    }
                    System.out.println(house.saleVolume.get());
                } finally {
                    //必须回收自定义的TreadLocal变量,尤其在线程池场景下,线程会被经常复用,可能会造成
                    //内存泄漏
                    house.saleVolume.remove();
                }

            }, String.valueOf(i)).start();
        }
        TimeUnit.MICROSECONDS.sleep(300);
        System.out.println("共卖出:" + house.saleVolume.get());
    }
}

class House {
    int saleCount = 0;

    public synchronized void setSaleCount() {
        ++saleCount;
    }

    //    ThreadLocal threadLocal = new ThreadLocal(){
//        @Override
//        protected Integer initialValue(){
//            return 0;
//        }
//    };
    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);

    public void setSaleCountByThreadLocal() {
        saleVolume.set(1 + saleVolume.get());
    }
}
