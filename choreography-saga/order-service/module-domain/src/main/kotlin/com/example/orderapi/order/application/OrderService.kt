package com.example.orderapi.order.application

import com.example.orderapi.order.domain.dto.Order
import com.example.orderapi.order.domain.dto.OrderPurchase
import com.example.orderapi.order.domain.port.`in`.PurchaseOrderUseCase
import com.example.orderapi.order.domain.port.out.OrderPort
import com.example.orderapi.order.domain.port.out.PayOrderPort

class OrderService(
    private val orderPort: OrderPort,
    private val payOrderPort: PayOrderPort,
) : PurchaseOrderUseCase {
    override fun purchaseOrder(order: Order): OrderPurchase {
        val purchageProductByOrder = orderPort.purchaceProductByOrder(order)
        payOrderPort.payProductsProcessor(purchageProductByOrder)

        return purchageProductByOrder
    }
}