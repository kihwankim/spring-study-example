package com.example.payapi.order.entity

import com.example.common.domain.objectMapper
import com.example.common.event.RawMessage
import com.example.orderapi.order.domain.model.OrderPurchase

internal data class OrderFailRawCommand(
    val orderId: Long,
    val orderHashKey: String,
    val type: String,
    val payload: String,
) : RawMessage<Long> {
    companion object {
        fun from(purchase: OrderPurchase): OrderFailRawCommand {
            val payload = objectMapper.writeValueAsString(purchase)

            return OrderFailRawCommand(
                orderId = purchase.orderId,
                orderHashKey = purchase.orderHashKey,
                type = purchase::class.java.simpleName,
                payload = payload
            )
        }
    }

    override fun getEventIdentifier(): Long = orderId

    override fun getEventType(): String = type

    override fun getEventPayload(): String = payload

    override fun getEventHashKey(): String = orderHashKey
}
