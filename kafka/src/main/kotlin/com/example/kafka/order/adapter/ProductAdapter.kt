package com.example.kafka.order.adapter

import com.example.kafka.order.adapter.message.MessageSender
import com.example.kafka.order.adapter.message.dto.ProductMessageDto
import com.example.kafka.order.domain.Order
import com.example.kafka.order.domain.port.out.ProductValidatePort
import org.springframework.stereotype.Component

@Component
class ProductAdapter(
    private val messageSender: MessageSender
) : ProductValidatePort {
    override fun verifyProduct(order: Order) {
        messageSender.sendProductData(ProductMessageDto(order.orderId, order.orderName, order.count))
    }
}