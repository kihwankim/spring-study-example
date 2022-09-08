package com.example.kafka.order.domain.port.out

import com.example.kafka.order.domain.Order

interface PersistncePort {
    fun save(order: Order)

    fun findAll(): List<Order>
}