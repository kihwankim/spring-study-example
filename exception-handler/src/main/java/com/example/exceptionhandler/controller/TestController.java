package com.example.exceptionhandler.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/exception/{value}")
    public String makeNullPointException(@PathVariable("value") Integer value) {
        if (value == 0) {
            throw new NullPointerException();
        } else if (value == 1) {
            throw new RuntimeException();
        }

        return "Good";
    }

    @ExceptionHandler(NullPointerException.class)
    public Object nullex(Exception e) {
        System.err.println(e.getClass());
        return "myService";
    }
}
