package com.example.async.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {
    private final AsyncTaskExecutor executor;

    public void noraml(int i) {
        sleepTest(500, i);
    }

    @Async
    public void asyncMethod(int i) {
        sleepTest(500, i);
    }

    public void asyncWithThreadPool(List<Integer> indexs) {
        indexs.forEach(index -> asyncRunable(700, index));
    }

    public List<Integer> asyncWithWait(List<Integer> indexs) {
        List<CompletableFuture<Integer>> priceFutures = indexs.stream()
                .map(index -> CompletableFuture.supplyAsync(() -> {
                    sleepTest(700, index);
                    return index;
                }))
                .collect(Collectors.toList());

        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    private void sleepTest(int millis, int index) {
        try {
            Thread.sleep(millis);
            log.info("[AsyncMethod]" + "-" + index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void asyncRunable(int millis, int index) {
        executor.execute(() -> sleepTest(millis, index));
    }

    private Future<Integer> asyncCallable(int millis, int index) {
        return executor.submit(() -> {
            sleepTest(millis, index);
            return index;
        });
    }
}
