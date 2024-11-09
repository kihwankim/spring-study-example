package com.example.springdatajparelativeexample.order.enums

enum class OrderEventLogType(
    val desc: String,
) {
    NEW_ORDER_CREATION("주문 생성"),
    PRODUCT_BUYING("상품 구매"),
    PAYMENT_APPROVAL("결재 승인"),
    ;
}
