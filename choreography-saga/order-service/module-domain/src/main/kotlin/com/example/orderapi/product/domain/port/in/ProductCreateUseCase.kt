package com.example.orderapi.product.domain.port.`in`

import com.example.orderapi.product.domain.command.ProductCreateCommand

interface ProductCreateUseCase {
    fun registeItem(productCreateCommand: ProductCreateCommand): Long
}