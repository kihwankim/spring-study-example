package com.example.orderapi.pay.entity

import com.example.common.event.RawMessage
import com.example.orderapi.order.domain.dto.OrderPurchase
import com.fasterxml.jackson.databind.ObjectMapper

internal data class PayMessageCommand(
    private val orderId: Long,
    private val orderHashKey: String,
    private val type: String,
    private val payload: String,
) : RawMessage<Long> {
    companion object {
        private val objectMapper = ObjectMapper()

        fun from(purchase: OrderPurchase): PayMessageCommand {
            val payload = objectMapper.writeValueAsString(purchase)

            return PayMessageCommand(
                orderId = purchase.orderId,
                orderHashKey = purchase.orderHashKey,
                type = purchase.javaClass.name,
                payload = payload
            )
        }
    }

    override fun getId(): Long {
        return orderId
    }

    override fun getType(): String {
        return type
    }

    override fun getPayload(): String {
        return payload
    }

    override fun hashKey(): String {
        return orderHashKey
    }
}
