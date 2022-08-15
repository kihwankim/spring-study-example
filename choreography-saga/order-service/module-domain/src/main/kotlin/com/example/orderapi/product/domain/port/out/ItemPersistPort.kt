package com.example.orderapi.product.domain.port.out

import com.example.orderapi.product.domain.command.ProductCreateCommand
import com.example.orderapi.product.domain.model.Product

interface ItemPersistPort {
    fun registProduct(productCreateCommand: ProductCreateCommand): Product
}