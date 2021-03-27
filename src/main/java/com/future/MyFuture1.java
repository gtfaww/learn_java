package com.future;

import java.util.concurrent.CompletableFuture;

public class MyFuture1 {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油");
        });

        CompletableFuture<Double> future1 = future.thenApplyAsync((code) -> {
            return fetchPrice(code);
        });

        future1.thenAccept((result) -> {
            System.out.println("price: " + result);
        });

        Thread.sleep(2000);
    }

    static String queryCode(String name) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
