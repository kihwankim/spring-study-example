package com.example.orderapi.order.adapter

import com.example.orderapi.order.domain.dto.Order
import com.example.orderapi.order.domain.dto.OrderPurchase
import com.example.orderapi.order.domain.dto.OrderStatus
import com.example.orderapi.order.domain.port.out.OrderPort
import com.example.orderapi.order.repository.OrderRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class OrderAdapter(
    private val orderRepository: OrderRepository
) : OrderPort {
    @Transactional
    override fun purchageProductByOrder(order: Order): OrderPurchase {
        val orderEntity = orderRepository.findById(order.orderId).orElseThrow { IllegalStateException() }

        return OrderPurchase(
            orderId = 1L,
            orderStatus = OrderStatus.COMPLETED
        )
    }
}