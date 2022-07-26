package com.example.kafka.order.adapter.message

import com.example.kafka.order.adapter.message.dto.ProductMessageDto

interface MessageSender {
    fun sendProductData(productMessage: ProductMessageDto)
}