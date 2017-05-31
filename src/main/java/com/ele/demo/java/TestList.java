package com.ele.demo.java;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * 猜测list
 *
 * @author oukailiang
 * @create 2017-04-19 下午8:30
 */

public class TestList {

    public static void main(String[] args){
        List<List<Integer>> intList = new ArrayList<>();
        List<Integer> subIntList = new ArrayList<>();
        subIntList.add(1);
        subIntList.add(11);
        subIntList.add(2);
        subIntList.add(22);
        subIntList.add(3);
        subIntList.add(33);

        intList = Lists.partition(subIntList, 2);
        for (List<Integer> intL:intList){
            if(intL.get(0) == 1){
                intL.add(111);
            }

        }
        System.out.println(intList.size());
    }
}
