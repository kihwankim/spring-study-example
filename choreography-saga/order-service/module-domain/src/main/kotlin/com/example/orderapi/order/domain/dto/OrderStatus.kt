package com.example.orderapi.order.domain.dto

enum class OrderStatus(val description: String) {
    CREATED("주문 생성됨"), COMPLETED("주문 완료됨"), CANCELED("주문 취소됨");
}
