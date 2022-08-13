package com.example.springeventlistener.event;

import com.example.springeventlistener.service.TestNextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterfaceEventHandler {
    private final TestNextService testNextService;

    @EventListener
    public void handleEvent(RawEvent event) {
        log.info("raw Event");
        testNextService.runNext(event);
    }

    @EventListener
    public void handleFirstEvent(InterfaceFirstEvent event) {
        log.info("raw first Event");
        testNextService.runNext(event);
    }

    @EventListener
    public void handleSecEvent(InterfaceSecondEvent event) {
        log.info("raw sec Event");
        testNextService.runNext(event);
    }
}
