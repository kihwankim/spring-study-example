package com.example.payapi.pay.entity

import com.example.payapi.common.entity.BaseEntity
import com.example.payapi.pay.domain.model.PaymentType
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "payment")
internal data class PaymentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    val id: Long = 0L,
    @Enumerated
    val paymentType: PaymentType,
    val orderId: Long,
    val userId: Long,
    var price: BigDecimal
) : BaseEntity()