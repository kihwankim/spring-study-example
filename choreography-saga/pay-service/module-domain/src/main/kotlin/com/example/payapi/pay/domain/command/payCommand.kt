package com.example.payapi.pay.domain.command

import com.example.common.event.DomainEvent
import java.math.BigDecimal

data class PayCommand(
    val userId: Long,
    val orderId: Long,
    val totalPrice: BigDecimal,
)