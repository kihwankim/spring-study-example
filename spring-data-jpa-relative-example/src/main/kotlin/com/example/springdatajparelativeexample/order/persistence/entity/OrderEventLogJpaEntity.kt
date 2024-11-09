package com.example.springdatajparelativeexample.order.persistence.entity

import com.example.springdatajparelativeexample.common.persistence.entity.BaseTimeEntity
import com.example.springdatajparelativeexample.order.enums.OrderEventLogType
import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "order_event_log")
class OrderEventLogJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_event_log_id")
    val id: Long = 0L,
    @Enumerated(EnumType.STRING)
    val type: OrderEventLogType,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val order: OrderJpaEntity
) : BaseTimeEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderEventLogJpaEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
