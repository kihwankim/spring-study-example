package com.example.springeventlistener.service;

import com.example.springeventlistener.event.RawEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestNextService {

    public void runNext(RawEvent rawEvent) {
        log.info("class={}, run next, id={}", this.getClass(), rawEvent.toString());
    }
}
