package com.ele.demo.java;

/**
 * 测试数组的各个性质
 *
 * @author oukailiang
 * @create 2017-11-01 下午5:45
 */

public class TestArray {

    /**
     * 测试数组声明、创建、初始化
     */
    public static void testInit(){
        double[] db ;
        db = new double[10];
        int i = 1000000000;
        float f = 100000000010000000001000000000100000000.000000000000000000000000000000000000000000000000000000f;
        Object o = 1;
        System.out.println(1.99999999f==2f);
        System.out.println(10000000001000000.0f > Long.MAX_VALUE);
        System.out.println((double)2.5f);
        System.out.println((double)2.2f);


    }

    public static void main(String[] args) {
        //
        testInit();

    }
}
