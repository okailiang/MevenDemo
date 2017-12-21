package com.ele.demo.java;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author oukailiang
 * @create 2017-08-04 下午6:34
 */

public class TestDate {

    private static void test(){
        Date date = new Date();

    }

    private static void testBigDecimal(BigDecimal ad){
        System.out.println("ad =" + ad.toString());
        ad = BigDecimal.valueOf(55);
    }

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0");
        System.out.println(df.format(0.52666));
        System.out.println(Integer.parseInt("23"));
        System.out.println(Integer.valueOf("0"));
        System.out.println(String.format("满减%s", BigDecimal.valueOf(3)));

        BigDecimal ad = BigDecimal.valueOf(-1);
        testBigDecimal(ad);
        System.out.println("after ad = " + ad.toString());

    }
}
