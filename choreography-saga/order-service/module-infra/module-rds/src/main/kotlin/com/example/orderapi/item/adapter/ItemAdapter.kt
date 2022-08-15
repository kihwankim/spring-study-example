package com.example.orderapi.item.adapter

import com.example.orderapi.item.entity.ItemEntity
import com.example.orderapi.item.entity.ItemUpdateLockStatus
import com.example.orderapi.item.repository.ItemRepository
import com.example.orderapi.product.domain.command.ProductCreateCommand
import com.example.orderapi.product.domain.model.Product
import com.example.orderapi.product.domain.port.out.ItemPersistPort
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger { }

@Component
internal class ItemAdapter(
    private val itemRepository: ItemRepository,
) : ItemPersistPort {

    @Transactional
    override fun registProduct(productCreateCommand: ProductCreateCommand): Product {
        val newItem = ItemEntity(
            price = productCreateCommand.price,
            inventory = productCreateCommand.inventory,
            registeredUserId = productCreateCommand.userId,
            productName = productCreateCommand.productName,
            description = productCreateCommand.description,
            status = ItemUpdateLockStatus.STABLE,
        )
        logger.info("product create id:  ${newItem.id}")

        return itemRepository.save(newItem).toProduct()
    }
}