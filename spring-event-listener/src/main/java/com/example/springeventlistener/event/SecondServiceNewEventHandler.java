package com.example.springeventlistener.event;

import com.example.springeventlistener.service.TestSecService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecondServiceNewEventHandler {
    private final TestSecService testSecService;

    @EventListener
    public void handlerFirst(TestNewEvent event) {
        testSecService.runNew(event.getId());
    }
}
