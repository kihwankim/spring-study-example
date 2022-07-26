package com.example.kafka.order.adapter.presentation.api

import com.example.kafka.order.domain.Order
import com.example.kafka.order.domain.port.`in`.OrderReceiptUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderReceiptUseCase: OrderReceiptUseCase
) {
    @GetMapping("/order-test")
    fun order(): ResponseEntity<Unit> {
        val order = Order(
            1L,
            "name",
            10
        )
        orderReceiptUseCase.orderProduct(order)

        return ResponseEntity.ok().build()
    }
}