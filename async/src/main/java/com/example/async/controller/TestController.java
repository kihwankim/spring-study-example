package com.example.async.controller;

import com.example.async.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("async")
    public String testAsync() {
        log.info("TEST ASYNC");
        for (int i = 0; i < 50; i++) {
            testService.asyncMethod(i);
        }
        return "";
    }
}
