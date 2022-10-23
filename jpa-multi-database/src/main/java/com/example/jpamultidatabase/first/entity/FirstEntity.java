package com.example.jpamultidatabase.first.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "first")
public class FirstEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
