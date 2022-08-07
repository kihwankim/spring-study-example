package com.example.orderapi.order.entity

import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
internal data class OrderEvents(
    @OneToMany(mappedBy = "orders", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    val orderEvents: MutableList<OrderEventEntity> = ArrayList()
)
