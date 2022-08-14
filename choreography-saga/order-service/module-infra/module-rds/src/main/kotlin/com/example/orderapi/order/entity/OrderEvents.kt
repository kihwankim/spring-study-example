package com.example.orderapi.order.entity

import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
internal data class OrderEvents(
    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    val orderEvents: MutableList<OrderEventEntity> = ArrayList()
) {
    fun addEvent(event: OrderEventEntity) {
        orderEvents.add(event)
    }

    fun getLastEvent(): OrderEventEntity {
        if (orderEvents.isEmpty()) {
            throw IllegalStateException("event not exsit")
        }
        return orderEvents.last()
    }
}
