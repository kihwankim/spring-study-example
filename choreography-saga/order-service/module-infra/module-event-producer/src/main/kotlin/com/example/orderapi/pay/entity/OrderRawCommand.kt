package com.example.orderapi.pay.entity

import com.example.common.domain.objectMapper
import com.example.common.event.RawMessage
import com.example.orderapi.order.domain.model.OrderPurchase

internal data class OrderRawCommand(
    val orderId: Long,
    val orderHashKey: String,
    val type: String,
    val payload: String,
) : RawMessage<Long> {
    companion object {
        fun from(purchase: OrderPurchase): OrderRawCommand {
            val payload = objectMapper.writeValueAsString(purchase)

            return OrderRawCommand(
                orderId = purchase.orderId,
                orderHashKey = purchase.orderHashKey,
                type = purchase::class.java.simpleName,
                payload = payload
            )
        }
    }

    override fun eventIdentifier(): Long = orderId

    override fun eventType(): String = type

    override fun eventRawMessagePayload(): String = payload

    override fun eventHashKeyValue(): String = orderHashKey
}
