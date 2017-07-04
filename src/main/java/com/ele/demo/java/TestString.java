package com.ele.demo.java;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author oukailiang
 * @create 2017-05-25 上午10:26
 */

public class TestString {

    private static void testStrIndexOf() {
        String str = "-123-45-6---7-";
        int index = str.indexOf("-");
        System.out.println(index);
        System.out.println(str.substring(index));
        String[] strArray = str.split("-");

        StringBuilder sb = new StringBuilder("1");
        System.out.println(sb.substring(0,0));
    }

    public static void main(String[] args) {
        //
        testStrIndexOf();

        String str = "尊敬的代理商，您对#cityName#的入驻流程已经通过审核，系统为您新建了城市经理账号：#accountName#， 初始密码：#pswd#，您可以通过此账号对#cityName#进行管理，您也可以在管理员账号下进行统一管理。具体事宜，请咨询当地渠道经理。饿了么期待与您合作愉快。";
        System.out.println(str.length());

        String jsonStr = "[\"http://fuss.alpha.elenet.me:9090/b/7c/03ac19a0b48259b4c13c90cd346c0png.png\",\"http://fuss.alpha.elenet.me:9090/b/e4/ac0b871f31ca976165fecb3f9c32cpng.png\"]";
        List<String> strList = JSON.parseArray(jsonStr, String.class);
        System.out.println();

    }
}
