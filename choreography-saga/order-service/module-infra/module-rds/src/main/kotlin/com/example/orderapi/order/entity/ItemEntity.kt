package com.example.orderapi.order.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "item")
data class ItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val price: BigDecimal,
    var productName: String,
    val quantity: Int,
    @Enumerated(EnumType.STRING)
    val status: ItemUpdateLockStatus
) {
    fun removeQuantity(numberOfQuantity: Int) {
        quantity - numberOfQuantity
    }
}
