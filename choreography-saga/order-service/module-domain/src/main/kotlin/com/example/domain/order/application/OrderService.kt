package com.example.domain.order.application

import com.example.domain.order.domain.dto.Order
import com.example.domain.order.domain.dto.OrderPurchase
import com.example.domain.order.domain.port.`in`.PurchaseOrderUseCase
import com.example.domain.order.domain.port.out.OrderPort
import com.example.domain.order.domain.port.out.PayOrderPort

class OrderService(
    private val orderPort: OrderPort,
    private val payOrderPort: PayOrderPort,
) : PurchaseOrderUseCase {


    override fun purchaseOrder(order: Order): OrderPurchase {
        val purchageProductByOrder = orderPort.purchageProductByOrder(order)
        payOrderPort.payProductsProcessor(purchageProductByOrder)

        return purchageProductByOrder
    }
}