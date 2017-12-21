package com.ele.demo;

/**
 * @author oukailiang
 * @create 2017-09-21 下午7:58
 */

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Main {


    /*请完成下面这个函数，实现题目要求的功能
    当然，你也可以不按照下面这个模板来作答，完全按照自己的想法来 ^-^
    ******************************开始写代码******************************/
    static String change(int m, int n) {
        StringBuilder sb = new StringBuilder();
        Map<Integer, String> map = new HashMap<>();
        map.put(10,"A");map.put(11,"B");
        map.put(12,"C");map.put(13,"D");
        map.put(14,"E");map.put(15,"F");
        while(m >= n){
            Integer b = m % n;
            if(b < 10){
                sb = sb.insert(0, b);
            }else{
                sb = sb.insert(0, map.get(b));
            }
            m =  m / n;
        }
        sb = sb.insert(0, m) ;
        return sb.toString();

    }
    /******************************结束写代码******************************/


    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String res;

        int _m;
        _m = Integer.parseInt(in.nextLine().trim());

        int _n;
        _n = Integer.parseInt(in.nextLine().trim());

        res = change(_m, _n);
        System.out.println(res);
    }
}
