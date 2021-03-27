package com.future;

import java.util.concurrent.CompletableFuture;

public class MyFuture {
    public static void main(String[] args) throws InterruptedException {

        CompletableFuture<Double> future = CompletableFuture.supplyAsync(MyFuture::fetchPrice);

        future.thenAccept((result) -> {
            System.out.printf("price: " + result);
        });

        future.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });

        Thread.sleep(200);

    }

    static Double fetchPrice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }
        return 5 + Math.random() * 20;
    }
}
