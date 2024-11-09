package com.example.springdatajparelativeexample.order.persistence.repository

import com.example.springdatajparelativeexample.order.persistence.entity.OrderNotCascadeLogJpaEnity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderNotCascadeLogJpaRepository : JpaRepository<OrderNotCascadeLogJpaEnity, Long>