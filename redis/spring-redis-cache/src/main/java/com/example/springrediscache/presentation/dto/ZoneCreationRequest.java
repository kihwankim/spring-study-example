package com.example.springrediscache.presentation.dto;

import com.example.springrediscache.infrastucture.domain.Zone;
import lombok.Data;

@Data
public class ZoneCreationRequest {
    private String name;


    public static Zone from(ZoneCreationRequest request) {
        return new Zone(request.getName());
    }
}
