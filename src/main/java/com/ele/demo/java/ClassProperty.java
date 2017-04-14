package com.ele.demo.java;

import com.ele.model.Order;
import com.ele.model.OrderOpt;
import com.ele.model.Voucher;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * class相关属性
 *
 * @author oukailiang
 * @create 2017-04-14 下午3:26
 */

public class ClassProperty {

    public static void testClassProperty() {
        Order order = new Order();
        OrderOpt order1 = new OrderOpt();
        order.setShopCount(1);
        order.setUserCount(2);
        order.setId(11L);
        order.setOrderNo("20170405");
        System.out.println("" + order.toString());
        Class c = order.getClass();
        Class[] cArr = c.getClasses();

        try {
            AnnotatedType[] annotatedTypes = c.getAnnotatedInterfaces();
            Annotation[] annotations = c.getAnnotations();
            Annotation[] annotationsDe = c.getDeclaredAnnotations();
            String canonical = c.getCanonicalName();
            String simpleName  = c.getSimpleName();
            String name = c.getName();

            ClassLoader classLoader = c.getClassLoader();
            Class voucherClass = classLoader.loadClass("com.ele.model.Voucher");
            Voucher voucher = (Voucher) voucherClass.newInstance();

            ClassLoader classLoaderParent = classLoader.getParent();
            Constructor[] constructor = c.getConstructors();
            Constructor[] constructorDe = c.getDeclaredConstructors();

            Field[] fieldsDe = c.getDeclaredFields();
            Field[] fields = c.getFields();

            Method[] methodsDe = c.getDeclaredMethods();
            Method[] methods = c.getMethods();
            Class encloseClass = c.getEnclosingClass();
            Class[] interfaceClass = c.getInterfaces();
            int modifierCount = c.getModifiers();

            Field field = c.getField("private java.lang.Long com.ele.model.Order.id");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println(order1.getId());
    }

    public static void main(String[] args) {
        testClassProperty();
    }
}
