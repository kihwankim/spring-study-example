package com.example.springeventlistener.event;

import lombok.Getter;

@Getter
public class TestNewEvent {

    private final long id;

    public TestNewEvent(long id) {
        this.id = id;
    }
}
