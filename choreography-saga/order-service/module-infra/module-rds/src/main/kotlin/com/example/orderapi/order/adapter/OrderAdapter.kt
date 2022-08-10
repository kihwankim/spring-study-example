package com.example.orderapi.order.adapter

import com.example.orderapi.order.domain.dto.Order
import com.example.orderapi.order.domain.dto.OrderPurchase
import com.example.orderapi.order.domain.dto.OrderStatus
import com.example.orderapi.order.domain.dto.Product
import com.example.orderapi.order.domain.port.out.OrderPort
import com.example.orderapi.order.entity.ItemEntity
import com.example.orderapi.order.entity.ItemUpdateLockStatus
import com.example.orderapi.order.entity.OrderEntity
import com.example.orderapi.order.repository.ItemQueryRepository
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
    override fun purchaceProductByOrder(order: Order): OrderPurchase {
        // item 조회
        val productMapById = createProductMap(order.products)
        val productIds = order.products.map { it.productId }

        // getting lock in db
        itemQueryRepository.updateStatus(ItemUpdateLockStatus.UPDATE, productIds)
        val foundItems: List<ItemEntity> = itemQueryRepository.findByItemIdIn(productIds)

        val createOrderEntity = OrderEntity.createOrder(order.userId)

        // order entity 에 order item 저장 및 quantity 줄이기 작업 진행
        for (itemEntity: ItemEntity in foundItems) {
            productMapById[itemEntity.id]?.let {
                createOrderEntity.registerItem(itemEntity, it.quantity)
            } ?: throw IllegalStateException("item 조회 실패")
        }

        val savedOrderEntity = orderRepository.save(createOrderEntity)

        itemQueryRepository.updateStatus(ItemUpdateLockStatus.STABLE, productIds)

        savePurchaceOutBox(savedOrderEntity.orderKey)

        return OrderPurchase(
            orderId = savedOrderEntity.id,
            orderStatus = OrderStatus.CREATED,
            orderHashKey = savedOrderEntity.orderKey
        )
    }

    private fun createProductMap(product: List<Product>): Map<Long, Product> = product.associateBy { it.productId }

    private fun savePurchaceOutBox(hasKey: String) {
        orderOutBoxRepository.save(OrderOutBoxEntity(eventType = ExternalEventType.PURCHASE, identityHashKey = hasKey))
    }
}