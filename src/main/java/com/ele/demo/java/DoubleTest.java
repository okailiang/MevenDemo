package com.ele.demo.java;

import java.math.BigDecimal;

/**
 * @author oukailiang
 * @create 2017-06-02 下午7:36
 */

public class DoubleTest {

    public static void main(String[] args) {
        double a = 0.06;
        double b = 0.01;
        System.out.println(a + b);
        BigDecimal bigD = BigDecimal.valueOf(a);
        bigD = bigD.add(BigDecimal.valueOf(b));
        System.out.println(bigD.doubleValue());

        BigDecimal c = BigDecimal.valueOf(8.00);
        System.out.println(BigDecimal.valueOf(c.longValue()));
    }
}
