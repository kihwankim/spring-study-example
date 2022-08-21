package com.example.payapi.order.entity

import com.example.common.event.DomainPayload
import com.example.payapi.order.domain.model.OrderProductItem
import com.example.payapi.order.domain.model.OrderStatus
import com.example.payapi.pay.domain.command.PayCommand
import java.math.BigDecimal
import kotlin.reflect.KClass

enum class OrderEventMessageType(
    val type: String,
    val classType: KClass<*>
) {
    ORDER_PURCHASE(OrderPurchase::class.simpleName!!, OrderPurchase::class);

    companion object {
        fun findBySimepleType(simpleType: String): OrderEventMessageType = OrderEventMessageType.values().find { it.type == simpleType }
            ?: kotlin.run { throw IllegalStateException("find failed: $simpleType") }
    }
}

data class OrderPurchase(
    val orderId: Long,
    val userId: Long,
    val totalPrice: BigDecimal,
    val orderStatus: OrderStatus,
    val orderHashKey: String,
    val orderProductItems: MutableList<OrderProductItem>,
) : DomainPayload<Long> {
    override fun getId(): Long = orderId

    fun toPayCommand() = PayCommand(userId = this.userId, orderId = this.orderId, totalPrice = this.totalPrice)
}
