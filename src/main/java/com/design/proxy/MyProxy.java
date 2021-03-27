package com.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.Executors;

public class MyProxy {
    public static void main(String[] args) {
        Object hello = new HelloImpl();
        InvocationHandler handler = new MyInvocationHandler(hello);

        Hello proxyHello = (Hello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(), HelloImpl.class.getInterfaces(), handler);

        proxyHello.SayHello();

        Executors.newFixedThreadPool(8);


    }

}
