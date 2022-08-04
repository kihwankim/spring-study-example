package com.example.domain.order.domain.port.out

import com.example.domain.order.domain.dto.Order
import com.example.domain.order.domain.dto.OrderPurchase

interface OrderPort {
    fun purchageProductByOrder(order: Order): OrderPurchase
}