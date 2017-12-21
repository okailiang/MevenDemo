package com.ele.demo;

import java.io.File;

/**
 * 获得系统文件路径
 *
 * @author oukailiang
 * @create 2016-11-17 上午10:34
 */

public class GetSystemPath {

    public static void getUserSysPath() {
        Long l = new Long(0l);
        System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
        File directory = new File("..");//设定为当前文件夹
        try {
            System.out.println(directory.getCanonicalPath());//获取标准的路径
            System.out.println(directory.getAbsolutePath());//获取绝对路径
            System.out.println(System.getProperty("user.home"));
            System.out.println(System.getProperty("user.name"));
            System.out.println(System.getProperty("java.io.tmpdir"));
            System.out.println(System.getProperties().toString());
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        getUserSysPath();
    }
}