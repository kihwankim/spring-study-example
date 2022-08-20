package com.example.payapi.pay.port.out

import com.example.payapi.pay.domain.event.OrderPaySuccessEvent

interface PaySuccessHandlerPort {

    fun sendOrderPaySucessEvent(orderPaySuccessEvent: OrderPaySuccessEvent)
}