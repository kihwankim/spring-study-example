package com.example.logginglab.controller;

import com.example.logginglab.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/logging/error")
    public ResponseEntity<Void> error() {
        testService.errorPrint();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logging/info")
    public ResponseEntity<Void> info() {
        testService.infoPrint();
        return ResponseEntity.ok().build();
    }
}
