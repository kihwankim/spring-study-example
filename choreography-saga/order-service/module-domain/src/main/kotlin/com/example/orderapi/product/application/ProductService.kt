package com.example.orderapi.product.application

import com.example.orderapi.product.domain.command.ProductCreateCommand
import com.example.orderapi.product.domain.port.`in`.ProductCreateUseCase
import com.example.orderapi.product.domain.port.out.ItemPersistPort

class ProductService(
    private val itemPersistPort: ItemPersistPort
) : ProductCreateUseCase {
    override fun registeItem(productCreateCommand: ProductCreateCommand): Long = itemPersistPort.registProduct(productCreateCommand).productId
}