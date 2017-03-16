package com.ele;

import java.util.regex.Pattern;

/**
 * 程序
 *
 * @author oukailiang
 * @create 2016-09-09 上午10:13
 */

public class Program {

    public static void main(String[] args) {
        String input = "年号1";
        // System.out.println(input.getBytes().length);
        // System.out.println(input.toCharArray().length);
        System.out.print(getStringLength(input));


//        String reg="^[1-9]{1}[0-9]{0,19}$";
//        String reg1="^[0-9]{1,9}([.][0]{1,2}){0,1}$";
//        System.out.println(Pattern.compile(reg1).matcher("1.0").matches());
        //  System.out.print("n=" + calculateN(1, 10));
    }

    /**
     * @param rest 剩了多少个
     * @param day  吃了多少天
     * @return
     */
    public static int calculateN(int rest, int day) {
        while (day > 0) {
            rest = (rest + 1) * 2;
            day--;
        }
        return rest;
    }

    public static int getStringLength(String str) {
        int strLength = 0;
        for (int i = 0; i < str.length(); i++) {
            String strTemp = str.substring(i, i + 1);
            if (strTemp.matches("[\u4e00-\u9fa5]")) {
                strLength += 2;
            } else {
                strLength += 1;
            }
        }
        return strLength;
    }
}
