package com.example.logginglab.other;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Slf4j
@Service
public class OtherLoggingService {
    @PostConstruct
    void setUp() {
        infoPrint();
        errorPrint();
    }

    public void infoPrint() {
        IntStream.range(0, 10)
                .forEach(intValue -> log.info("other value: {}", intValue));
    }

    public void errorPrint() {
        IntStream.range(0, 10)
                .forEach(intValue -> log.error("other value: {}", intValue));
    }
}
