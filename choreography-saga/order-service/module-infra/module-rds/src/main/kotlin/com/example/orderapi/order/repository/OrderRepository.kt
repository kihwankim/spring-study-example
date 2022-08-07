package com.example.orderapi.order.repository

import com.example.orderapi.order.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

internal interface OrderRepository : JpaRepository<OrderEntity, Long>