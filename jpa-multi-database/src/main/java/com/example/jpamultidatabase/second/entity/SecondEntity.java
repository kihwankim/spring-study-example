package com.example.jpamultidatabase.second.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "second")
@NoArgsConstructor
public class SecondEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String newName;

    public SecondEntity(String name, String newName) {
        this.name = name;
        this.newName = newName;
    }
}
