package com.example.springeventlistener.event;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InterfaceFirstEvent implements RawEvent {
    private final long id;
    private final Long nameId;

    public InterfaceFirstEvent(long id, long nameId) {
        this.id = id;
        this.nameId = nameId;
    }
}
