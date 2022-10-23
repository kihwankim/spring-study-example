package com.example.jpamultidatabase.first.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "first")
@NoArgsConstructor
public class FirstEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String newName;

    public FirstEntity(String name, String newName) {
        this.name = name;
        this.newName = newName;
    }
}
