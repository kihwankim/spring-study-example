package com.example.orderapi.order.application

import com.example.orderapi.order.domain.command.OrderCreateCommand
import com.example.orderapi.order.domain.command.OrderPayEvent
import com.example.orderapi.order.domain.model.OrderPurchase
import com.example.orderapi.order.domain.port.`in`.PayEventHandleUseCase
import com.example.orderapi.order.domain.port.`in`.PurchaseOrderUseCase
import com.example.orderapi.order.domain.port.out.OrderPort
import com.example.orderapi.order.domain.port.out.PayOrderPort

class OrderService(
    private val orderPort: OrderPort,
    private val payOrderPort: PayOrderPort,
) : PurchaseOrderUseCase, PayEventHandleUseCase {
    override fun purchaseOrder(orderCreateCommand: OrderCreateCommand) {
        val saveOrder = orderPort.purchaceProductByOrder(orderCreateCommand)
        val orderPurchase = OrderPurchase(
            orderId = saveOrder.orderId,
            userId = saveOrder.userId,
            totalPrice = saveOrder.totalPrice,
            orderStatus = saveOrder.orderStatus,
            orderHashKey = saveOrder.nowEventKey,
            orderProductItems = saveOrder.orderProductItems
        )

        payOrderPort.payProductsProcessor(orderPurchase)
    }

    override fun markPaySuccess(orderPayEvent: OrderPayEvent) {
        orderPort.markPaySuccess(orderPayEvent)
    }

    override fun markFail(orderPayEvent: OrderPayEvent) {
        orderPort.markFail(orderPayEvent)
    }
}