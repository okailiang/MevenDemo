package com.ele.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.json.JsonStructure;
import java.util.Map;

/**
 * xml工具类
 *
 * @author oukailiang
 * @create 2016-09-18 下午7:45
 */

public class XmlUtils {

    /**
     * 将xml串转换为map对象
     *
     * @param xmlStr
     * @return
     */
    public static Map<String, Object> parse(String xmlStr) throws DocumentException {

        Document doc = DocumentHelper.parseText(xmlStr);
        Element element = doc.getRootElement();
        element.elements();
        return null;
    }
}
