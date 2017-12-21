package com.ele.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @author oukailiang
 * @create 2017-12-04 下午3:02
 */

public class JSONTest {


    public static void main(String[] args) {
//        String str = "[{\"id\":12,\"name\":\"张三\"},{\"id\":123,\"name\":\"张三3\"}]";
//        JSONArray jsonArray = JSON.parseArray(str);
//
//        Long id = 0L;
//        for (int i = 0; i < jsonArray.size(); i++) {
//            id = jsonArray.getJSONObject(i).getLong("ids");
//        }
//
//        System.out.println(id);
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread();
            System.out.println( thread.getId());
        }

    }
}
