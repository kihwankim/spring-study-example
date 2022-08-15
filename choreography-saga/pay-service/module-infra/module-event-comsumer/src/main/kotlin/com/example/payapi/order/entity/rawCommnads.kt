package com.example.payapi.order.entity

import com.example.common.event.RawMessage

internal data class OrderRawCommand(
    private val orderId: Long,
    private val orderHashKey: String,
    private val type: String,
    private val payload: String,
) : RawMessage<Long> {

    override fun getId(): Long = orderId

    override fun getType(): String = type

    override fun getPayload(): String = payload

    override fun hashKey(): String = orderHashKey
}
