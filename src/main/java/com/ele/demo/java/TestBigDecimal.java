package com.ele.demo.java;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author oukailiang
 * @create 2017-08-04 下午5:24
 */

public class TestBigDecimal {

    public static void testBigDecimal() {
        BigDecimal a = BigDecimal.valueOf(1.22);

        System.out.println(a.toString());

        BigDecimal b = a.setScale(1, BigDecimal.ROUND_FLOOR);

        System.out.println(b.toString());

        System.out.println(checkOnePoint(a));
        System.out.println(checkOnePoint(b));
        System.out.println(checkOnePoint(BigDecimal.valueOf(122)));
        System.out.println(checkOnePoint(BigDecimal.valueOf(0.1)));
        System.out.println(checkOnePoint(BigDecimal.valueOf(0)));

        System.out.println(checkOnePoint(BigDecimal.valueOf(0.0)));
        System.out.println(checkOnePoint(BigDecimal.valueOf(-1)));
        System.out.println("=============");
        System.out.println(checkMoney(BigDecimal.valueOf(499.00)));
        System.out.println(checkMoney(BigDecimal.valueOf(1.2)));
        System.out.println(checkMoney(BigDecimal.valueOf(0)));
        System.out.println(checkMoney(BigDecimal.valueOf(-1)));
        System.out.println(checkMoney(BigDecimal.valueOf(500)));

    }
    public static boolean checkMoney(BigDecimal money){
        if(money.compareTo(BigDecimal.ZERO) < 0 || money.compareTo(BigDecimal.valueOf(500)) >= 0){
            return false;
        }
        return checkOnePoint(money);
    }

    public static boolean checkOnePoint(BigDecimal a) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,1})?$");
        return pattern.matcher(a.toString()).matches();
    }

    public static void main(String[] args) {
        testBigDecimal();

    }
}
