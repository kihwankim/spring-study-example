package com.example.springeventlistener.event;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InterfaceSecondEvent implements RawEvent {
    private final long id;
    private final String name;

    public InterfaceSecondEvent(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
