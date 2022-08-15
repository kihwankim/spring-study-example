package com.example.orderapi.order.domain.model

import com.example.common.domain.DomainModel
import java.math.BigDecimal

data class Order(
    val orderId: Long, // order 식별 id
    val userId: Long, // 구메자 id
    val orderStatus: OrderStatus, // 현재 order 상태
    val totalPrice: BigDecimal,
    val nowEventKey: String,
    val orderProductItems: List<OrderProductItem>, // 구메할 데이터
) : DomainModel<Order, Long> {
    override fun getId(): Long = orderId
}
