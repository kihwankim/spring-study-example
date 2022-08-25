package com.example.redisexample.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Name implements Serializable {
    private final String firstName;
    private final String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
