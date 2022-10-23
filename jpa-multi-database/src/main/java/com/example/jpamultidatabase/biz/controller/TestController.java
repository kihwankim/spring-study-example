package com.example.jpamultidatabase.biz.controller;

import com.example.jpamultidatabase.biz.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/call/command")
    public ResponseEntity<Void> saveAll() {
        testService.saveEachOne();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/call/find")
    public ResponseEntity<Void> findAll() {
        testService.readAll();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/call/update")
    public ResponseEntity<Void> update() {
        testService.updateNames();
        return ResponseEntity.ok().build();
    }
}
