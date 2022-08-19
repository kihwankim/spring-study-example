package com.example.payapi.pay.adapter

import com.example.common.domain.ActResult
import com.example.payapi.pay.domain.command.PayCommand
import com.example.payapi.pay.domain.model.PaymentType
import com.example.payapi.pay.entity.PaymentEntity
import com.example.payapi.pay.port.out.PaymentPort
import com.example.payapi.pay.repository.PaymentRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
@Transactional(readOnly = true)
internal class PaymentAdapter(
    private val paymentRepository: PaymentRepository
) : PaymentPort {

    @Transactional
    override fun pay(payCommand: PayCommand): ActResult<Long> {
        val paymentEntity = PaymentEntity(paymentType = PaymentType.CARD, userId = payCommand.userId, orderId = payCommand.orderId, price = payCommand.totalPrice)
        val savedPayment = paymentRepository.save(paymentEntity)

        return ActResult.success(savedPayment.id)
    }
}