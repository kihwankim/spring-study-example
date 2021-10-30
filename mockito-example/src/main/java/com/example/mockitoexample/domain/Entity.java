package com.example.mockitoexample.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Entity {
    private Long id;
    private String name;

    @Builder
    public Entity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
