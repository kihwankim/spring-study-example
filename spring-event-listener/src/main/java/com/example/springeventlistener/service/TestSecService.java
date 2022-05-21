package com.example.springeventlistener.service;

import com.example.springeventlistener.event.TestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestSecService {

    public void run(long testId) {
        log.info("class={}, run, id={}", this.getClass(), testId);
    }
}
