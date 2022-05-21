package com.example.springeventlistener.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TestEvent extends ApplicationEvent {
    private final long testId;

    public TestEvent(Object source, long testId) {
        super(source);
        this.testId = testId;
    }
}
