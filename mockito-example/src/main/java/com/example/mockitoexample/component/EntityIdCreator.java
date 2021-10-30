package com.example.mockitoexample.component;

import org.springframework.stereotype.Component;

@Component
public class EntityIdCreator {
    private long id = 0;

    public long generateNewId() {
        long generateId = id;
        id++;
        return generateId;
    }
}
