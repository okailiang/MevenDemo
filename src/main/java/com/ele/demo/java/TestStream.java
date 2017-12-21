package com.ele.demo.java;

import com.ele.model.Order;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author oukailiangH
 * @create 2017-08-16 下午4:22
 */

public class TestStream {
    private static void testStreamNoReturn(List<Integer> list) {
        list = list.stream().filter(l -> l != 1).collect(Collectors.toList());
        System.out.println(list.toString());
    }

    private static void testStream1(List<Order> list) {
        list = list.stream().filter(l -> l.getId() != 1).collect(Collectors.toList());
        System.out.println(list.toString());
    }

    private static void testList1(List<Order> list) {
        list.remove(0);
        System.out.println(list.toString());
    }


    /**
     * List为empty能不能进行stream，结果是可以的
     */
    private static void testStream() {
        List<Order> orders = new ArrayList<>();

        Order order = new Order();
        orders = orders.stream().filter(o -> o.getId() == 3).collect(Collectors.toList());
        System.out.println();

    }


    public static void main(String[] args) {
        testStream();
        //
        List<Integer> list = Arrays.asList(1, 5, 6, 7, 8);
        testStreamNoReturn(list);
        System.out.println(list.toString());

        Order order = new Order();
        order.setId(1L);
        order.setOrderNo("123");
        Order order1 = new Order();
        order1.setId(2L);
        order1.setOrderNo("222");
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);orderList.add(order1);
        testStream1(orderList);
        System.out.println(orderList.toString());

        testList1(orderList);
        System.out.println(orderList.toString());

    }
}