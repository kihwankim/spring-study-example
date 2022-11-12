package com.example.springrediscache.presentation.dto;

import com.example.springrediscache.infrastucture.domain.Zone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneResponse implements Serializable {
    private Long id;
    private String name;

    public static ZoneResponse from(Zone zone) {
        return new ZoneResponse(zone.getId(), zone.getName());
    }
}
