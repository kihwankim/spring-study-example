package com.example.orderapi.order.domain.port.out

import com.example.orderapi.order.domain.dto.OrderPurchase

interface PayOrderPort {

    fun payProductsProcessor(orderPurchase: OrderPurchase)
}