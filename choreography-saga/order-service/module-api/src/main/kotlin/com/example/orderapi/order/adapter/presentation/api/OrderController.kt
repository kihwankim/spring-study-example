package com.example.orderapi.order.adapter.presentation.api

import com.example.orderapi.order.adapter.presentation.dto.OrderPurchaseRequest
import com.example.orderapi.order.domain.port.`in`.PurchaseOrderUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val purchaseOrderUseCase: PurchaseOrderUseCase,
) {
    @PostMapping("/api/v1/orders") // command
    fun orderProducts(@RequestBody orderPurchaseRequest: OrderPurchaseRequest): ResponseEntity<Unit> {
        purchaseOrderUseCase.purchaseOrder(orderPurchaseRequest.toOrderCommand())
        return ResponseEntity.ok().build()
    }
}