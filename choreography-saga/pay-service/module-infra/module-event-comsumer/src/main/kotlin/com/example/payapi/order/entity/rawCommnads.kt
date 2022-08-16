package com.example.payapi.order.entity

import com.example.common.event.RawMessage

internal data class OrderRawCommand(
    val orderId: Long,
    val orderHashKey: String,
    val type: String,
    val payload: String,
) : RawMessage<Long> {

    override fun eventIdentifier(): Long = orderId

    override fun eventType(): String = type

    override fun eventRawMessagePayload(): String = payload

    override fun eventHashKeyValue(): String = orderHashKey
}
