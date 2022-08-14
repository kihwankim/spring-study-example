package com.example.orderapi.order.entity

import com.example.orderapi.common.entity.BaseEntity
import com.example.orderapi.order.domain.model.OrderStatus
import javax.persistence.*

@Entity
@Table(name = "order_event")
internal data class OrderEventEntity(
    @Id
    @Column(name = "order_event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var orderStatus: OrderStatus,
    val orderKey: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    var order: OrderEntity
) : BaseEntity()