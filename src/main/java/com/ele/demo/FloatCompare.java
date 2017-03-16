package com.ele.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 浮点类型比较
 *
 * @author oukailiang
 * @create 2016-12-19 下午3:35
 */

public class FloatCompare {
    /**
     * 通过基本类型比较
     */
    public static void floatCompareByBaseType() {
        float a = 1.0000001f, b = 1.00000002f;
        //超过浮点型7位就无法比较
        System.out.println("a == b:" + (a == b));
        System.out.println("floatToRawIntBits(a) == floatToRawIntBits(b):" + (Float.floatToRawIntBits(a) == Float.floatToRawIntBits(b)));
        // System.out.println("a compare b:" + (Float.compare(a, b)));
        System.out.println("a == b:" + (a - b));
        System.out.println("a == b:" + (Float.hashCode(a)) + " raw =" + Float.floatToRawIntBits(a));
        Float a1 = Float.NaN;
        Float b1 = 0.0000001f;
        Float.compare(a1, b1);
        double i = 1.0 / 0;
        System.out.println(i);             //Infinity
        System.out.println(i + 1);         //Infinity
        System.out.println(i == i + 1);    //true

        i = 0.0 / 0;
        System.out.println(i);             //NaN
        System.out.println(i + 1);         //NaN
        System.out.println(i == i + 1);    //false


    }

    public static void intCompare() {

        Integer a = 0;
        Integer b = -0;
        System.out.println("a == b:" + (a.compareTo(b)));
    }

    public static void main(String[] args) {
        // intCompare();
        // floatCompareByBaseType();

        while (true) {
            System.out.println((int) (Math.random() * 2));
        }


    }
}
