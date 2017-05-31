package com.ele.demo.java;

import org.w3c.dom.ls.LSInput;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author oukailiang
 * @create 2017-05-08 下午1:30
 */

public class TestDouble {

    public static void main(String[] args) {
        //Double a = null;
        //System.out.println(a + 1.0);
//        List<String> intList = new ArrayList<>();
//        intList.addAll(null);
//        System.out.println(intList.size());
//        System.out.println(intList.isEmpty());
        Map<String, Long> map = new HashMap<>();
        map.put("ab", 5L);
        map.put("cd", 10L);
        System.out.println(map.containsKey("ab"));
        System.out.println(map.containsKey("cd"));
        System.out.println(map.containsKey("bc"));
        System.out.println(map.keySet().contains("cd"));
        System.out.println(map.keySet().contains("bc"));
        System.out.println(map.size());

        List<Long> strKey  = new ArrayList<>(map.values());

        map.remove("cd");
        map.remove("ab");
        map.remove("bc");
        System.out.println(map.size() + "|" + map.get("ab") + "|" + map.get("cd"));
        List<String> strList = Arrays.asList("12");
        Map<String, String> strMap = strList.stream().filter(s -> s == "").collect(Collectors.toMap(s -> s, s -> s));
        System.out.println(strMap.toString());
        strList.addAll(new ArrayList<>());
        System.out.println(strList.size());

    }
}
