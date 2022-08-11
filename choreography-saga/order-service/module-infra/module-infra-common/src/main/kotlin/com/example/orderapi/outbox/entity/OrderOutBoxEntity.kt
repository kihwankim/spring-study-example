package com.example.orderapi.outbox.entity

import com.example.orderapi.outbox.common.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "order_out_box")
data class OrderOutBoxEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val eventType: ExternalEventType,
    @Column(nullable = false, unique = true)
    val identityHashKey: String,
) : BaseEntity()
