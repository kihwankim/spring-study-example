package com.example.orderapi.order.entity

import com.example.common.event.RawMessage

internal data class OrderRawEvent(
    val orderId: Long,
    val orderHashKey: String,
    val type: String,
    val payload: String,
) : RawMessage<Long> {
    override fun getEventIdentifier(): Long = orderId

    override fun getEventType(): String = type

    override fun getEventPayload(): String = payload

    override fun getEventHashKey(): String = orderHashKey
}
