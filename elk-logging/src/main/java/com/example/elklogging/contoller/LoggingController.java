package com.example.elklogging.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoggingController {

    @GetMapping("/test/{id}")
    public ResponseEntity<Void> response(@PathVariable("id") Long id) {
        if (id == 0) {
            log.info("hello var={}", id);
        } else if (id == 1) {
            log.info("hello var 1 == {}", id);
        }

        return ResponseEntity.ok().build();
    }
}
