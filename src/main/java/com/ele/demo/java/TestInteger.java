package com.ele.demo.java;

/**
 * @author oukailiang
 * @create 2017-07-31 下午2:15
 */

public class TestInteger {
    private static final Integer i = 10;

    private static final Long l = 100L;

    private static final String str = "hahaha";


    private static void testInteger(Object o) {
        if (o instanceof Integer) {
            System.out.println(o);
        }

        if (o instanceof Long) {
            System.out.println(o);
        }

    }

    public static void main(String[] args) {
        testInteger(i);
    }
}
