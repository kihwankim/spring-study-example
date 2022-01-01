package com.example.rsetdocs.controller;

import com.example.rsetdocs.dto.HelloRequest;
import com.example.rsetdocs.dto.HelloResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @GetMapping("/api/v1/hello")
    public ResponseEntity<HelloResponse> hello(HelloRequest helloRequest) {
        HelloResponse helloResponse = new HelloResponse();
        helloResponse.setId(helloRequest.getId());
        helloResponse.setReplyMessage("reply: " + helloRequest.getMessage());

        return ResponseEntity.ok(helloResponse);
    }
}
