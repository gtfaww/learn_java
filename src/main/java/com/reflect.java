package com;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Test {

    public void target(int i) {
//        new Exception("#" + i).printStackTrace();

    }

    public void target1(int i) {
//        new Exception("#" + i).printStackTrace();

    }

    public void target2(int i) {
//        new Exception("#" + i).printStackTrace();

    }
}

public class reflect {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("com.company.Test");
        Object o = c.newInstance();

//        Constructor con = c.getConstructor();
//        con.setAccessible(true);

        Object[] arg = new Object[1];
        arg[0] = 128;

        Method method = c.getMethod("target", int.class);
        method.setAccessible(true);

//        polluteProfile(o);

        long current = System.currentTimeMillis();
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }

            method.invoke(o, arg);
        }


    }

    public static void polluteProfile(Object o) throws Exception {
        Method method1 = Test.class.getMethod("target", int.class);
        Method method2 = Test.class.getMethod("target", int.class);
        for (int i = 0; i < 2000; i++) {
            method1.invoke(o, 0);
            method2.invoke(o, 0);
        }
    }

}
