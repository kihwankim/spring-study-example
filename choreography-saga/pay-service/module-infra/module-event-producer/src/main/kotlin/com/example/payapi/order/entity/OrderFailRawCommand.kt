package com.example.payapi.order.entity

import com.example.common.domain.objectMapper
import com.example.common.event.RawMessage
import com.example.common.event.keyGenerate
import com.example.payapi.pay.domain.command.PayCommand

internal data class OrderFailRawCommand(
    val orderId: Long,
    val orderHashKey: String,
    val type: String,
    val payload: String,
) : RawMessage<Long> {

    companion object {
        private const val KEY_PREFIX = "pay-fail"
        fun from(payCommand: PayCommand): OrderFailRawCommand {
            val payload = objectMapper.writeValueAsString(payCommand)

            return OrderFailRawCommand(
                orderId = payCommand.orderId,
                orderHashKey = keyGenerate(KEY_PREFIX),
                type = payCommand::class.java.simpleName,
                payload = payload
            )
        }
    }

    override fun getEventIdentifier(): Long = orderId

    override fun getEventType(): String = type

    override fun getEventPayload(): String = payload

    override fun getEventHashKey(): String = orderHashKey
}
