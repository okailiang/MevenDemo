package com.ele.demo.java;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 猜测list
 *
 * @author oukailiang
 * @create 2017-04-19 下午8:30
 */

public class TestList {
    private static void testSubListSplit() {
        List<List<Integer>> intList = new ArrayList<>();
        List<Integer> subIntList = new ArrayList<>();
        subIntList.add(1);
        subIntList.add(11);
        subIntList.add(2);
        subIntList.add(22);
        subIntList.add(3);
        subIntList.add(33);

        intList = Lists.partition(subIntList, 2);
        for (List<Integer> intL : intList) {
            if (intL.get(0) == 1) {
                intL.add(111);
            }

        }
        System.out.println(intList.size());
    }

    private static void testSubList() {
        List<String> stringList = Arrays.asList("a", "c", "v", "y", "b", "d", "f", "g", "h", "u", "e", "d", "d", "8", "9");
        int size = stringList.size();
        List<String> subList = stringList.subList(0, 3);
        subList = stringList.subList(1 * 3, 2 * 3);
        subList = stringList.subList(0, size);

    }

    public static void main(String[] args) {
        //testSubListSplit();
        testSubList();

        String result = String.format("导入合同数据[%d]条", 9);
        System.out.println(result);
    }
}
