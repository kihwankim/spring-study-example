package com.example.jpamultidatabase.second.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "second")
public class SecondEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
