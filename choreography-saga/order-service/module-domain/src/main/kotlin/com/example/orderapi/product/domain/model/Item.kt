package com.example.orderapi.product.domain.model

import com.example.common.domain.DomainModel
import java.math.BigDecimal

data class Item(
    val itemId: Long,
    val quantity: Int,
    val price: BigDecimal,
) : DomainModel<Item, Long> {
    override fun getId(): Long = this.itemId
}
