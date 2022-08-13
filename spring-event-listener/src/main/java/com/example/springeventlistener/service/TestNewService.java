package com.example.springeventlistener.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestNewService {

    public void run(long testId) {
        log.info("class={}, run, id={}", this.getClass(), testId);
    }

    public void runNew(long testId) {
        log.info("class={}, run new, id={}", this.getClass(), testId);
        log.info("class={}, run end, id={}", this.getClass(), testId);
    }
}
