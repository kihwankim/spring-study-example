package com.example.springdatajparelativeexample.order.persistence.entity

import com.example.springdatajparelativeexample.order.enums.OrderEventLogType
import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class OrderEventLogsEmbedded(
    @OneToMany(cascade = [CascadeType.PERSIST], mappedBy = "order")
    var logs: MutableList<OrderEventLogJpaEntity> = mutableListOf()
) {
    fun addNewLog(
        order: OrderJpaEntity,
        type: OrderEventLogType,
    ) {
        logs.add(
            OrderEventLogJpaEntity(
                type = type,
                order = order,
            )
        )
    }
}
