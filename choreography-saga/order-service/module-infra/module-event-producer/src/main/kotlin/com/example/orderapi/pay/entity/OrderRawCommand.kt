package com.example.orderapi.pay.entity

import com.example.common.event.RawMessage
import com.example.orderapi.order.domain.model.OrderPurchase
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

internal data class OrderRawCommand(
    private val orderId: Long,
    private val orderHashKey: String,
    private val type: String,
    private val payload: String,
) : RawMessage<Long> {
    companion object {
        private val objectMapper = ObjectMapper()
            .registerModule(JavaTimeModule())

        fun from(purchase: OrderPurchase): OrderRawCommand {
            val payload = objectMapper.writeValueAsString(purchase)

            return OrderRawCommand(
                orderId = purchase.orderId,
                orderHashKey = purchase.orderHashKey,
                type = purchase.javaClass.name,
                payload = payload
            )
        }
    }

    override fun getId(): Long = orderId

    override fun getType(): String = type

    override fun getPayload(): String = payload

    override fun hashKey(): String = orderHashKey
}
