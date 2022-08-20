package com.example.payapi.pay.config

import com.example.payapi.pay.application.PayService
import com.example.payapi.pay.port.out.PaySuccessHandlerPort
import com.example.payapi.pay.port.out.PaymentFailHandlerPort
import com.example.payapi.pay.port.out.PaymentPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PayAppConfig {

    @Bean
    fun payService(
        paymentPort: PaymentPort,
        paySuccessHandlerPort: PaySuccessHandlerPort,
        paymentFailHandlerPort: PaymentFailHandlerPort
    ): PayService {
        return PayService(paymentPort, paySuccessHandlerPort, paymentFailHandlerPort)
    }
}