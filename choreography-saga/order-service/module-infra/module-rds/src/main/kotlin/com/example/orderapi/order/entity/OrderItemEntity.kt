package com.example.orderapi.order.entity

import com.example.orderapi.common.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "order_item")
internal data class OrderItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    val order: OrderEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    val item: ItemEntity,
    val itemCount: Int
) : BaseEntity()
