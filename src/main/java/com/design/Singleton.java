package com.design;

public class Singleton {
    private Singleton() {
    }

    private static class LazyInstance {
        static final Singleton s = new Singleton();
    }

    public static Singleton getInstance() {
        return LazyInstance.s;
    }

}