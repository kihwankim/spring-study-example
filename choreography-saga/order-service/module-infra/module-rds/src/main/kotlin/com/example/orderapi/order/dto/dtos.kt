package com.example.orderapi.order.dto

import com.example.orderapi.item.entity.ItemEntity
import com.example.orderapi.order.domain.command.PurchaseProduct

internal data class OrderItemDto(
    val quantity: Int,
    val item: ItemEntity,
) {
    companion object {
        fun toListFromEntities(items: List<ItemEntity>, purchaseList: List<PurchaseProduct>): List<OrderItemDto> {
            val productIdKeyWithPurchaseMap = purchaseList.associateBy { it.productId }
            verifyItems(items, productIdKeyWithPurchaseMap.keys)

            return items.map { itemEntity ->
                OrderItemDto(
                    quantity = productIdKeyWithPurchaseMap[itemEntity.id]!!.productQuantity,
                    item = itemEntity
                )
            }
        }

        private fun verifyItems(items: List<ItemEntity>, productIds: Set<Long>) {
            if (items.size != productIds.size) {
                throw IllegalStateException("error")
            }

            val itemIdsSet = items.asSequence().map { it.id }.toSet()
            productIds.forEach {
                if (it !in itemIdsSet) throw IllegalStateException("error")
            }
        }
    }
}