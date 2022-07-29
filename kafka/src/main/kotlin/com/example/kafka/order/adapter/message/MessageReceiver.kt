package com.example.kafka.order.adapter.message

import com.example.kafka.order.adapter.message.dto.ConsumerMessageDto

interface MessageReceiver {
    fun receive(consumerMessageDto: ConsumerMessageDto)
}