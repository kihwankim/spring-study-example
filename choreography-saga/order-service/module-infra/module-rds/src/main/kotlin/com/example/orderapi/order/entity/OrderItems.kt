package com.example.orderapi.order.entity

import java.math.BigDecimal
import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
internal data class OrderItems(
    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    val orderItems: MutableList<OrderItemEntity> = ArrayList()
) {
    fun calculateTotalPrice(): BigDecimal {
        return orderItems.asSequence()
            .map { it.item.price }
            .reduce { prevResult, nextVal -> prevResult.add(nextVal) }
    }

}