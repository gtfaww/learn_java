package com;

public class SingletonTest {
    
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        System.out.printf(instance.toString());
    }
}
