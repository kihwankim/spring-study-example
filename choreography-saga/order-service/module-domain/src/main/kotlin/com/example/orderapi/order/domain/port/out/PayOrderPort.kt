package com.example.orderapi.order.domain.port.out

import com.example.orderapi.order.domain.model.OrderPurchase

interface PayOrderPort {

    fun payProductsProcessor(orderPurchase: OrderPurchase)
}