package com.example.payapi.order.entity

import com.example.common.domain.objectMapper
import com.example.common.event.DomainPayload
import com.example.common.event.RawMessage
import com.example.common.event.keyGenerate

internal data class OrderRawEvent(
    val orderId: Long,
    val orderHashKey: String,
    val type: String,
    val payload: String,
) : RawMessage<Long> {

    companion object {
        private const val KEY_PREFIX = "order"

        fun from(domainPayload: DomainPayload<Long>): OrderRawEvent {
            val payload = objectMapper.writeValueAsString(domainPayload)

            return OrderRawEvent(
                orderId = domainPayload.getId(),
                orderHashKey = keyGenerate(KEY_PREFIX),
                type = domainPayload::class.java.simpleName,
                payload = payload
            )
        }
    }

    override fun getEventIdentifier(): Long = orderId

    override fun getEventType(): String = type

    override fun getEventPayload(): String = payload

    override fun getEventHashKey(): String = orderHashKey
}
