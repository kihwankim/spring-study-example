package com.example.orderapi.pay.adapter

import com.example.orderapi.order.domain.dto.OrderPurchase
import com.example.orderapi.order.domain.port.out.PayOrderPort
import org.springframework.stereotype.Component

@Component
class PayOrderAdapter : PayOrderPort {
    override fun payProductsProcessor(orderPurchase: OrderPurchase) {
        TODO("Not yet implemented")
    }
}