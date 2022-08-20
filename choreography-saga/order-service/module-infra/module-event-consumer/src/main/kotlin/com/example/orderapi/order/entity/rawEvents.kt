package com.example.orderapi.order.entity

import com.example.common.event.DomainEvent
import java.math.BigDecimal
import kotlin.reflect.KClass


enum class OrderEventType(
    val type: String,
    val classType: KClass<*>
) {
    ORDER_FAIL(OrderPayFailEvent::class.simpleName!!, OrderPayFailEvent::class);

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
}