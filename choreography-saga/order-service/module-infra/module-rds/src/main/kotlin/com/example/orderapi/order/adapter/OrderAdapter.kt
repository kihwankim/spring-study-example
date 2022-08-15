package com.example.orderapi.order.adapter

import com.example.orderapi.item.entity.ItemEntity
import com.example.orderapi.item.entity.ItemUpdateLockStatus
import com.example.orderapi.item.repository.ItemQueryRepository
import com.example.orderapi.order.domain.command.OrderCreateCommand
import com.example.orderapi.order.domain.model.Order
import com.example.orderapi.order.domain.port.out.OrderPort
import com.example.orderapi.order.dto.OrderItemDto
import com.example.orderapi.order.entity.OrderEntity
import com.example.orderapi.order.repository.OrderRepository
import com.example.orderapi.outbox.entity.ExternalEventType
import com.example.orderapi.outbox.entity.OrderOutBoxEntity
import com.example.orderapi.outbox.repository.OrderOutBoxRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class OrderAdapter(
    private val orderRepository: OrderRepository,
    private val itemQueryRepository: ItemQueryRepository,
    private val orderOutBoxRepository: OrderOutBoxRepository,
) : OrderPort {
    @Transactional
    override fun purchaceProductByOrder(orderCreateCommand: OrderCreateCommand): Order {
        // item 조회
        val productIds = orderCreateCommand.purchaseProducts.map { it.productId }

        // getting lock in db
        itemQueryRepository.updateStatus(ItemUpdateLockStatus.UPDATE, productIds)
        val foundItems: List<ItemEntity> = itemQueryRepository.findByItemIdIn(productIds)
        val orderItemDtos = OrderItemDto.toListFromEntities(foundItems, orderCreateCommand.purchaseProducts)

        // save order entity
        val savedOrderEntity = orderRepository.save(OrderEntity.createOrder(orderCreateCommand.userId, orderItemDtos))

        // change status
        itemQueryRepository.updateStatus(ItemUpdateLockStatus.STABLE, productIds)

        val savedOrder = savedOrderEntity.toOrder()

        savePurchaceOutBox(savedOrder.nowEventKey)

        return savedOrder
    }

    private fun savePurchaceOutBox(hasKey: String) {
        orderOutBoxRepository.save(OrderOutBoxEntity(eventType = ExternalEventType.PURCHASE, identityHashKey = hasKey))
    }
}