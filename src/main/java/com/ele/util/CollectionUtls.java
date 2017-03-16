package com.ele.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 集合工具类
 *
 * @author oukailiang
 * @create 2016-09-19 下午12:59
 */

public class CollectionUtls {

    /**
     * org.apache.commons.beanutils.BeanUtils实现
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        org.apache.commons.beanutils.BeanUtils.populate(obj, map);

        return obj;
    }

    public static Map<?, ?> objectToMap(Object obj) {
        if (obj == null)
            return null;

        return new org.apache.commons.beanutils.BeanMap(obj);
    }

    /**
     * 反射实现
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject1(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();
        Object mapValue;
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            mapValue = map.get(field.getName());
            setFieldValue(field, mapValue, obj);
        }
        return obj;
    }

    public static void setFieldValue(Field field, Object mapValue, Object obj) throws Exception {
        if (mapValue == null) {
            field.set(obj, mapValue);
            return;
        }
        String value = mapValue.toString();
        String type = field.getType().getName();
        if (type.equals("java.lang.String")) {
            field.set(obj, value);
        } else if (type.equals("java.lang.Integer")) {
            field.set(obj, Integer.parseInt(value));
        } else if (type.equals("java.lang.Long")) {
            field.set(obj, Long.parseLong(value));
        } else if (type.equals("java.math.BigDecimal")) {
            field.set(obj, BigDecimal.valueOf(Double.parseDouble(value)));
        } else if (type.equals("java.math.BigInteger")) {
            field.set(obj, BigInteger.valueOf(Long.parseLong(value)));
        } else if (type.equals("java.util.Date")) {
            field.set(obj, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
        } else if (type.equals("java.lang.Boolean")) {
            field.set(obj, Boolean.valueOf(value));
        } else if (type.equals("java.lang.Float")) {
            field.set(obj, Float.parseFloat(value));
        } else if (type.equals("java.lang.Double")) {
            field.set(obj, Double.parseDouble(value));
        } else if (type.equals("java.lang.byte[]")) {
            field.set(obj, value.getBytes());
        } else {
            field.set(obj, value);
        }
    }

    public static Map<String, Object> objectToMap1(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
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
    public static Object mapToObject2(Map<String, Object> map, Class<?> beanClass) throws Exception {
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

    public static Map<String, Object> objectToMap2(Object obj) throws Exception {
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

}
