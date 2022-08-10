package com.example.orderapi.order.entity


import com.example.common.event.keyGenerate
import com.example.orderapi.common.entity.BaseEntity
import com.example.orderapi.order.domain.dto.OrderStatus
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "orders")
internal data class OrderEntity(
    @Id
    @Column(name = "orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val orderKey: String,
    var totalPrice: BigDecimal = BigDecimal.ZERO,
    val userId: Long,
    @Enumerated(EnumType.STRING)
    var status: OrderStatus,
    @Embedded
    val orderEvents: OrderEvents = OrderEvents(),
    @Embedded
    val orderItems: OrderItems = OrderItems(),
) : BaseEntity() {
    companion object {
        private const val KEY_PREFIX = "order"

        fun createOrder(userId: Long): OrderEntity {
            val newOrder = OrderEntity(
                userId = userId,
                orderKey = keyGenerate(KEY_PREFIX),
                status = OrderStatus.CREATED
            )

            newOrder.orderEvents.addEvent(
                OrderEventEntity(
                    orderStatus = OrderStatus.CREATED,
                    order = newOrder
                )
            )

            return newOrder
        }
    }

    fun registerItem(inputItem: ItemEntity, numberOfQuantity: Int) {
        inputItem.removeQuantity(numberOfQuantity)
        orderItems.orderItems.add(OrderItemEntity(order = this, item = inputItem, itemCount = numberOfQuantity))
    }
}