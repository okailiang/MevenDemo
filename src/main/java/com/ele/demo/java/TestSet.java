package com.ele.demo.java;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试set
 *
 * @author oukailiang
 * @create 2017-06-11 上午10:48
 */

public class TestSet {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();

        set.add("a");
        set.add("b");
        set.add("a");
        System.out.println(set.size());
    }
}
