package com.example.async.controller;

import com.example.async.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("normal") // 2.5
    public String testNoraml() {
        log.info("TEST ASYNC");
        for (int i = 0; i < 50; i++) {
            testService.noraml(i);
        }
        return "";
    }

    @GetMapping("async") // thread pool exceed then thred pool -> error
    public String testAsync() {
        log.info("TEST ASYNC");
        for (int i = 0; i < 50; i++) {
            testService.asyncMethod(i);
        }
        return "";
    }

    @GetMapping("async-pool") // 22 ms
    public String testAsyncWithPool() {
        log.info("TEST ASYNC POOL");

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        testService.asyncWithThreadPool(list);
        return "";
    }

    @GetMapping("async-future") // 4.3
    public List<Integer> testAsyncWithCompleteFuture() {
        log.info("TEST async-future POOL");

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }

        return testService.asyncWithWait(list);
    }
}
