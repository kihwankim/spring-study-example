package com.example.payapi.pay.repository

import com.example.payapi.pay.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal interface PaymentRepository : JpaRepository<PaymentEntity, Long>