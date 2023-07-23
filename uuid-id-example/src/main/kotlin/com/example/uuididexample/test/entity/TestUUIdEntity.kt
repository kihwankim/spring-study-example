package com.example.uuididexample.test.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "test_uuid")
class TestUUIdEntity(
    @Id
    @UuidGenerator
    @Column(name = "test_uuid_id")
    val id: String? = null,
    val name: String,
)