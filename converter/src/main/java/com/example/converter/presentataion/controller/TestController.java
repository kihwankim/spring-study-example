package com.example.converter.presentataion.controller;

import com.example.converter.presentataion.dto.DataRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<DataRequest> request(DataRequest dataRequest) {
        return ResponseEntity.ok(dataRequest);
    }
}
