package com.example.springeventlistener.service;

import com.example.springeventlistener.event.TestEvent;
import com.example.springeventlistener.event.TestNewEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestFirstService {

    private final ApplicationEventPublisher publisher;

    public void run(long id) {
        log.info("class={}, run start, id={}", this.getClass(), id);
        publisher.publishEvent(new TestEvent(this, id));
        log.info("class={}, run end, id={}", this.getClass(), id);
    }

    public void runNew(long id) {
        log.info("class={}, run new start, id={}", this.getClass(), id);
        publisher.publishEvent(new TestNewEvent(id));
        log.info("class={}, run new end, id={}", this.getClass(), id);
    }
}
