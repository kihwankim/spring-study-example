package com.example.springdatajparelativeexample.order.presentation

import com.example.springdatajparelativeexample.order.enums.OrderEventLogType
import com.example.springdatajparelativeexample.order.service.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderService: OrderService
) {

    @GetMapping("/api/v1/orders/new")
    fun createNewOrder(@RequestParam name: String): OrderCreateResponse {
        return OrderCreateResponse(id = orderService.saveOrder(name))
    }

    @GetMapping("/api/v1/orders/{id}/new-log")
    fun addNewLog(@PathVariable id: Long, @RequestParam type: OrderEventLogType) {
        orderService.addNewLog(id, type)
    }

    @GetMapping("/api/v1/orders/{id}/change-name")
    fun changeName(@PathVariable id: Long, @RequestParam name: String) {
        orderService.changeName(id, name)
    }

    @GetMapping("/api/v1/orders/{id}")
    fun changeName(@PathVariable id: Long) = orderService.findJust(id)
}

data class OrderCreateResponse(
    val id: Long
)
