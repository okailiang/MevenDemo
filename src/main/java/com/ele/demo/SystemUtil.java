package com.ele.demo;

import com.alibaba.fastjson.JSON;

/**
 * @author oukailiang
 * @create 2017-03-16 下午5:21
 */

public class SystemUtil {


    public static void main(String[] args) {

        System.out.println("env:" + JSON.toJSONString(System.getenv()));
        System.out.println("Properties:" + JSON.toJSONString(System.getProperties()));
        System.out.println("nanoTime:" + JSON.toJSONString(System.nanoTime()));
        System.out.println("currentTimeMillis:" + JSON.toJSONString(System.currentTimeMillis()));
        System.out.println("lineSeparator:" + JSON.toJSONString(System.lineSeparator()) + "huhu");
        System.out.println("user.home:" + System.getProperty("user.home"));


    }
}
