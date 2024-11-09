package com.example.springdatajparelativeexample.order.persistence.entity

import com.example.springdatajparelativeexample.common.persistence.entity.BaseTimeEntity
import com.example.springdatajparelativeexample.order.enums.OrderEventLogType
import org.hibernate.annotations.DynamicUpdate
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@DynamicUpdate
@Table(name = "orders")
class OrderJpaEntity(
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var name: String,
    @Version
    val version: Long = 0L,
    @Embedded
    val logs: OrderEventLogsEmbedded = OrderEventLogsEmbedded(),
    @OneToMany(mappedBy = "order")
    var notCascadeLogs: MutableList<OrderNotCascadeLogJpaEnity> = mutableListOf()
) : BaseTimeEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderJpaEntity) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun addNewLog(type: OrderEventLogType) {
        this.logs.addNewLog(
            this,
            type
        )
    }

    fun addNewNotCascadeLog(
        data: OrderNotCascadeLogJpaEnity,
        isUpdatedAt: Boolean
    ) {
        notCascadeLogs.add(data)
        if (isUpdatedAt) {
            updateNew()
        }
    }
}
