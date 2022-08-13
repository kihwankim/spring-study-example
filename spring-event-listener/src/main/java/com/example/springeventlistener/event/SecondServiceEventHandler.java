package com.example.springeventlistener.event;

import com.example.springeventlistener.service.TestNewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecondServiceEventHandler implements ApplicationListener<TestEvent> {
    private final TestNewService service;

    @Override
    public void onApplicationEvent(TestEvent event) {
        service.run(event.getTestId());
    }
}
