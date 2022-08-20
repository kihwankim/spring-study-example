package com.example.orderapi.order.entity

import com.example.common.event.DomainEvent
import com.example.orderapi.order.domain.command.OrderPayEvent
import com.example.orderapi.order.domain.model.OrderStatus
import java.math.BigDecimal
import kotlin.reflect.KClass


enum class OrderEventType(
    val type: String,
    val classType: KClass<*>
) {
    ORDER_PAY_FAIL(OrderPayFailEvent::class.simpleName!!, OrderPayFailEvent::class),
    ORDER_PAY_SUCCESS(OrderPaySuccessEvent::class.simpleName!!, OrderPaySuccessEvent::class);

    companion object {
        fun findBySimepleType(simpleType: String): OrderEventType = OrderEventType.values().find { it.type == simpleType }
            ?: kotlin.run { throw IllegalStateException("find failed: $simpleType") }
    }
}

data class OrderPayFailEvent(
    val orderId: Long,
    val userId: Long,
    val totalPrice: BigDecimal,
) : DomainEvent<Long> {
    override fun getId(): Long = orderId

    fun toOrderPayEvent(): OrderPayEvent {
        return OrderPayEvent(
            orderId = orderId,
            orderStatus = OrderStatus.CANCELED
        )
    }
}

data class OrderPaySuccessEvent(
    val orderId: Long,
    val payId: Long,
    val userId: Long,
    val totalPrice: BigDecimal,
) : DomainEvent<Long> {
    override fun getId(): Long = orderId

    fun toOrderPayEvent(): OrderPayEvent {
        return OrderPayEvent(
            orderId = orderId,
            orderStatus = OrderStatus.COMPLETED
        )
    }
}
