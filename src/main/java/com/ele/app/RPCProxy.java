package com.ele.app;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理实现RPC
 *
 * @author oukailiang
 * @create 2017-10-16 上午11:29
 */

public class RPCProxy implements InvocationHandler {

    private Object object;

    public RPCProxy(Object object) {
        this.object = object;
    }

    private static Object getProxy(Object object) {
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), new RPCProxy(object));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //结果参数
        Object result = new Object();
        //执行通信相关逻辑

        //
        return result;
    }
}
