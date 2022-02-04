package com.example.async;

import java.util.concurrent.*;

public class TestMain {
    public TestMain() throws InterruptedException {
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        Future<String> future = calculateAsync();
//        System.out.println(future.get());

        CompletableFuture<String> cf0 =
                CompletableFuture.failedFuture(new RuntimeException("Oops"));

        CompletableFuture<String> cf1 =
                cf0.whenComplete((msg, ex) -> {
                    if (ex != null) {
                        System.out.println("Exception occurred");
                    } else {
                        System.out.println(msg);
                    }
                    /*
                     * Cannot return value because method whenComplete
                     * is not designed to translate completion outcomes.
                     * It uses bi-consumer as input parameter:
                     * BiConsumer<? super T, ? super Throwable> action
                     */
                });

        try {
            cf1.join();
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Future<String> calculateAsyncWithCancellation() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.cancel(false);
            return null;
        });

        return completableFuture;
    }

    public static Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

}
