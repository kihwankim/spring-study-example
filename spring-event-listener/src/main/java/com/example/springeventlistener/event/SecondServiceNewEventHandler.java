package com.example.springeventlistener.event;

import com.example.springeventlistener.service.TestNewService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecondServiceNewEventHandler {
    private final TestNewService testNewService;

    @EventListener
    public void handlerFirst(TestNewEvent event) {
        testNewService.runNew(event.getId());
    }
}
