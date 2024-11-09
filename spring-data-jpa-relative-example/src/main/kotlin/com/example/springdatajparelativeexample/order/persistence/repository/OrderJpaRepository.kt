package com.example.springdatajparelativeexample.order.persistence.repository

import com.example.springdatajparelativeexample.order.persistence.entity.OrderJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import kotlin.jvm.optionals.getOrNull

interface OrderJpaRepository : JpaRepository<OrderJpaEntity, Long>

fun OrderJpaRepository.findByIdOrThrow(id: Long): OrderJpaEntity = this.findById(id).getOrNull()
    ?: throw IllegalArgumentException("Order with identifier $id not found")
