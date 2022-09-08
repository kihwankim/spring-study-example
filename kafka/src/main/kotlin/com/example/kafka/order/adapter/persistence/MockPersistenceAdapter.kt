package com.example.kafka.order.adapter.persistence

import com.example.kafka.order.domain.Order
import com.example.kafka.order.domain.port.out.PersistncePort
import org.springframework.stereotype.Repository

@Repository
class MockPersistenceAdapter(
    private val mockList: MutableList<Order>
) : PersistncePort {

    override fun save(order: Order) {
        mockList.add(order)
    }

    override fun findAll(): List<Order> = mockList
}