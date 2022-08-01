package com.Interview;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class ConcurrentHashMapDemo {
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static int RESIZE_STAMP_BITS = 16;
    /**
     * The bit shift for recording size stamp in sizeCtl.
     */
    private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;

    public static void main(String[] args) {

    }
    static final int resizeStamp(int n) {
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }


    private static void put(String key) {
        int hash = spread(key.hashCode());
        int i = ((16 - 1) & hash);

        System.out.println(hash);
    }

    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }

    private static void getMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "b");
        map.put("4", "b");
        map.put("5", "b");
        map.put("6", "b");
        map.put("7", "b");
        map.put("8", "b");
        map.put("9", "b");
        map.put("10", "b");
        map.put("11", "b");
        map.put("12", "b");
        map.put("13", "b");
        map.put("14", "b");
        map.put("15", "b");
        map.put("16", "b");
        map.put("17", "b");
        System.out.println(map);
    }
    private static final int tableSizeFor(Integer c) {
        int n = c - 1;
        System.out.println("n =="+n);
        n |= n >>> 1;
        System.out.println("n >>> 1->"+n);
        n |= n >>> 2;
        System.out.println("n >>> 2->"+n);
        n |= n >>> 4;
        System.out.println("n >>> 4->"+n);
        n |= n >>> 8;
        System.out.println("n >>> 8->"+n);
        n |= n >>> 16;
        System.out.println("n >>> 16->"+n);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
//1
//    10
//    11
//    100
}
