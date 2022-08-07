package com.example.orderapi.order.entity

import com.example.orderapi.order.domain.dto.OrderStatus
import com.example.orderapi.common.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "order_event")
internal data class OrderEventEntity(
    @Id
    @Column(name = "order_event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var orderStatus: OrderStatus,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    var orders: OrderEntity
) : BaseEntity()