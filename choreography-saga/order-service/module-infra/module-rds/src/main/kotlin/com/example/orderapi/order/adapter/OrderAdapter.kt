package com.example.orderapi.order.adapter

import com.example.orderapi.order.domain.dto.Order
import com.example.orderapi.order.domain.dto.OrderPurchase
import com.example.orderapi.order.domain.dto.OrderStatus
import com.example.orderapi.order.domain.port.out.OrderPort
import com.example.orderapi.order.repository.OrderRepository
import org.springframework.stereotype.Component

@Component
internal class OrderAdapter(
    private val orderRepository: OrderRepository
) : OrderPort {
    override fun purchageProductByOrder(order: Order): OrderPurchase {
        return OrderPurchase(
            orderId = 1L,
            orderStatus = OrderStatus.COMPLETED
        )
    }
}