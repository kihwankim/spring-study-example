package com.example.orderapi.item.entity

import com.example.orderapi.common.entity.BaseEntity
import com.example.orderapi.product.domain.model.Product
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "item")
internal data class ItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Version
    val version: Long = 0L,
    val registeredUserId: Long,
    val price: BigDecimal,
    var productName: String,
    val inventory: Int,
    var description: String,
    @Enumerated(EnumType.STRING)
    val status: ItemUpdateLockStatus
) : BaseEntity() {
    fun removeQuantity(numberOfQuantity: Int) {
        validateQuantity(numberOfQuantity)

        inventory - numberOfQuantity
    }

    private fun validateQuantity(numberOfOrderQuantity: Int) {
        if (inventory < numberOfOrderQuantity) {
            throw IllegalStateException("quantity is less exception Now Left Quantity: $inventory")
        }
    }

    fun toProduct(): Product {
        return Product(
            productId = this.id,
            name = this.productName,
            price = this.price,
            inventory = this.inventory,
            description = this.description,
            createTime = this.createdTime,
            updateTime = this.updateTime
        )
    }
}
