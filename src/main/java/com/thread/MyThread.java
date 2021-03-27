package com.thread;

public class MyThread {

    private int sharedState;

    public void nonSafe() {
        for (int i = 0; i < 100000; i++) {
                int former = sharedState++;
                int latter = sharedState;
                if (former != latter - 1) {
                    System.out.printf("Observed data race, former is " +
                            former + ", " + "latter is " + latter);
                }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                myThread.nonSafe();
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                myThread.nonSafe();
            }
        };

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
