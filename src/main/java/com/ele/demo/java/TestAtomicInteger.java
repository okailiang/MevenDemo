package com.ele.demo.java;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author oukailiang
 * @create 2017-06-12 下午5:09
 */

public class TestAtomicInteger {

    private static void testAtomicInteger(){
        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println(atomicInteger);
        System.out.println(atomicInteger.decrementAndGet());
        System.out.println(atomicInteger.addAndGet(2));
        System.out.println(atomicInteger.incrementAndGet());
    }

    public static void main(String[] args) {
        testAtomicInteger();


        String str = "3456787634x";
        System.out.println(str.toUpperCase());
    }
}
