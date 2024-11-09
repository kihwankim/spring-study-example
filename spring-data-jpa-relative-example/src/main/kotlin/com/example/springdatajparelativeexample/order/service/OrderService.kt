package com.example.springdatajparelativeexample.order.service

import com.example.springdatajparelativeexample.order.enums.OrderEventLogType
import com.example.springdatajparelativeexample.order.persistence.entity.OrderJpaEntity
import com.example.springdatajparelativeexample.order.persistence.entity.OrderNotCascadeLogJpaEnity
import com.example.springdatajparelativeexample.order.persistence.repository.OrderJpaRepository
import com.example.springdatajparelativeexample.order.persistence.repository.OrderNotCascadeLogJpaRepository
import com.example.springdatajparelativeexample.order.persistence.repository.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderJpaRepository: OrderJpaRepository,
    private val orderNotCascadeLogJpaRepository: OrderNotCascadeLogJpaRepository,
) {
    @Transactional
    fun saveOrder(name: String): Long {
        val order = OrderJpaEntity(
            name = name,
        )

        order.addNewLog(OrderEventLogType.NEW_ORDER_CREATION)

        return orderJpaRepository.save(order).id
    }

    @Transactional
    fun addNewNotCascadeLog(id: Long, type: OrderEventLogType, isUpdatedAt: Boolean) {
        val order = orderJpaRepository.findByIdOrThrow(id)
        orderNotCascadeLogJpaRepository.save(
            OrderNotCascadeLogJpaEnity(
                type = type,
                order = order
            )
        ).also {
            order.addNewNotCascadeLog(it, isUpdatedAt)
        }
    }

    @Transactional
    fun addNewLog(id: Long, type: OrderEventLogType) {
        val order = orderJpaRepository.findByIdOrThrow(id)
        order.addNewLog(type)
    }

    @Transactional
    fun changeName(id: Long, name: String) {
        val order = orderJpaRepository.findByIdOrThrow(id)
        order.name = name
    }

    @Transactional
    fun findJust(id: Long): Long {
        val order = orderJpaRepository.findByIdOrThrow(id)

        return order.id
    }
}