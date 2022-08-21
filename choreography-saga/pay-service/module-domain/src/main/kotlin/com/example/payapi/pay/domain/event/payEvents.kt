package com.example.payapi.pay.domain.event

import com.example.common.event.DomainPayload
import java.math.BigDecimal

data class OrderPayFailEvent(
    val orderId: Long,
    val userId: Long,
    val totalPrice: BigDecimal,
) : DomainPayload<Long> {
    override fun getId(): Long = orderId
}

data class OrderPaySuccessEvent(
    val orderId: Long,
    val payId: Long,
    val userId: Long,
    val totalPrice: BigDecimal,
) : DomainPayload<Long> {
    override fun getId(): Long = orderId
}
