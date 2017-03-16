package com.ele.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ele.model.weixin.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @ClassName: MessageUtil
 * @Description: 消息工具类
 * @author: souvc
 * @date Jun 15, 2015 4:35:12 PM
 */
public class WeiXinMsgUtils {

    /**
     * 解析微信发来的xml消息
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static Map parseXml(InputStream inputStream)
            throws Exception {
        // 将解析结果存储在HashMap中
        Map map = new HashMap();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        String tempKey;
        // 遍历所有子节点
        for (Element e : elementList) {
            tempKey = e.getName();
            //将第一个字母转为小写，供后面map转对象使用
            map.put(tempKey.substring(0, 1).toLowerCase() + tempKey.substring(1), e.getText());
        }
        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * 自省实现map转Java对象
     *
     * @param map       map<String,Object>
     * @param beanClass 要转为的类
     * @return 要转化的对象类
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();
        Object mapValue;

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                mapValue = map.get(property.getName());
                setterValue(property, mapValue, obj);
            }
        }
        return obj;
    }

    /**
     * 给Java对象设置值
     *
     * @param property 属性对象
     * @param mapValue 给属性赋值的值
     * @param object   Java对象
     */
    private static void setterValue(PropertyDescriptor property, Object mapValue, Object object) throws
            InvocationTargetException, IllegalAccessException, ParseException {
        Method setter = property.getWriteMethod();
        if (mapValue == null) {
            setter.invoke(object, mapValue);
            return;
        }

        Class propertyType = property.getPropertyType();
        String type = propertyType.getName();
        String value = mapValue.toString();

        if (type.equals("java.lang.String")) {
            setter.invoke(object, value);
        } else if (type.equals("java.lang.Integer")) {
            setter.invoke(object, Integer.parseInt(value));
        } else if (type.equals("java.lang.Long")) {
            setter.invoke(object, Long.parseLong(value));
        } else if (type.equals("java.math.BigDecimal")) {
            setter.invoke(object, BigDecimal.valueOf(Double.parseDouble(value)));
        } else if (type.equals("java.math.BigInteger")) {
            setter.invoke(object, BigInteger.valueOf(Long.parseLong(value)));
        } else if (type.equals("java.util.Date")) {
            setter.invoke(object, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
        } else if (type.equals("java.lang.Boolean")) {
            setter.invoke(object, Boolean.valueOf(value));
        } else if (type.equals("java.lang.Float")) {
            setter.invoke(object, Float.parseFloat(value));
        } else if (type.equals("java.lang.Double")) {
            setter.invoke(object, Double.parseDouble(value));
        } else if (type.equals("java.lang.byte[]")) {
            setter.invoke(object, value.getBytes());
        } else {
            setter.invoke(object, value);
        }
    }

    /**
     * 对象转为map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }
        return map;
    }
//    /**
//     * 文本消息对象转换成xml
//     *
//     * @param textMessage 文本消息对象
//     * @return xml
//     */
//    public static String textMessageToXml(TextMessage textMessage) {
//        xstream.alias("xml", textMessage.getClass());
//        return xstream.toXML(textMessage);
//    }
//
//    /**
//     * 音乐消息对象转换成xml
//     *
//     * @param musicMessage 音乐消息对象
//     * @return xml
//     */
//    public static String musicMessageToXml(MusicMessage musicMessage) {
//        xstream.alias("xml", musicMessage.getClass());
//        return xstream.toXML(musicMessage);
//    }
//

    /**
     * 图文消息对象转换成xml
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }
//
//    /**
//     * 将对象转换为xml
//     * @param baseEvent
//     * @return
//     */
//    public static String baseEventToXml(BaseEvent baseEvent){
//        xstream.alias("xml", baseEvent.getClass());
//        return xstream.toXML(baseEvent);
//    }

    /**
     * 将对象转换为xml
     *
     * @param object
     * @return
     */
    public static String toXml(Object object) {
        xstream.alias("xml", object.getClass());
        return xstream.toXML(object);
    }

    /**
     * 扩展xstream，使其支持CDATA块
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                public void startNode(String name, Class clazz) {
                    cdata = true;
                    if ("CreateTime".equals(name)) {
                        cdata = false;
                    }
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
}