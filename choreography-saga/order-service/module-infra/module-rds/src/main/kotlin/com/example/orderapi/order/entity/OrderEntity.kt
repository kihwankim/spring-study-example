package com.example.orderapi.order.entity

import com.example.orderapi.common.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "orders")
internal data class OrderEntity(
    @Id
    @Column(name = "orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val hashKey: String,
    @Embedded
    val orderEvents: OrderEvents = OrderEvents()
) : BaseEntity()
