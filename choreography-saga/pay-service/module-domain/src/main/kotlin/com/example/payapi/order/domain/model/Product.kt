package com.example.payapi.order.domain.model

import com.example.common.domain.DomainModel
import java.math.BigDecimal
import java.time.LocalDateTime

data class Product(
    val productId: Long, // id
    val name: String, // 상품 이름
    val price: BigDecimal, // 가격
    val inventory: Int, // 남은 상품 개수
    val description: String, // 상품 설명
    val createTime: LocalDateTime, // 생성 날짜
    val updateTime: LocalDateTime, // 수정 날짜
) : DomainModel<Product, Long> {
    override fun domainModelId(): Long = productId
}
