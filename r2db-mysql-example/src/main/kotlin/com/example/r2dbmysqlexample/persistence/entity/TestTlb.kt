package com.example.r2dbmysqlexample.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("test_tlb")
data class TestTlb(
    @Id
    val id: Long? = null,
    val name: String,
)