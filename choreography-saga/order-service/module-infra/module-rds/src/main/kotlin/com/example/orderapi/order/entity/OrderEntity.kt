package com.example.orderapi.order.entity


import com.example.common.event.keyGenerate
import com.example.orderapi.common.entity.BaseEntity
import com.example.orderapi.item.entity.ItemEntity
import com.example.orderapi.order.domain.model.Order
import com.example.orderapi.order.domain.model.OrderStatus
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "orders")
internal data class OrderEntity(
    @Id
    @Column(name = "orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Version
    val version: Long = 0L,

    val userId: Long,

    @Enumerated(EnumType.STRING)
    var status: OrderStatus,

    var totalPrice: BigDecimal = BigDecimal.ZERO,

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
                status = OrderStatus.CREATED
            )

            newOrder.orderEvents.addEvent(
                OrderEventEntity(
                    orderStatus = OrderStatus.CREATED,
                    orderKey = keyGenerate(KEY_PREFIX),
                    order = newOrder
                )
            )

            return newOrder
        }
    }

    private fun getLastEvent(): OrderEventEntity {
        return orderEvents.getLastEvent()
    }

    fun registerItem(inputItem: ItemEntity, numberOfQuantity: Int) {
        inputItem.removeQuantity(numberOfQuantity)
        orderItems.orderItems.add(OrderItemEntity(order = this, item = inputItem, quantity = numberOfQuantity))
        this.totalPrice = orderItems.calculateTotalPrice()
    }

    fun toOrder(): Order {
        return Order(
            orderId = this.id,
            userId = this.userId,
            orderStatus = this.status,
            nowEventKey = this.getLastEvent().orderKey,
            totalPrice = this.totalPrice,
            orderProductItems = this.orderItems.toOrderProductItems()
        )
    }
}