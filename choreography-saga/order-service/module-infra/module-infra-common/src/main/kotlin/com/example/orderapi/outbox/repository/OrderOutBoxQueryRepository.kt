package com.example.orderapi.outbox.repository

import com.example.orderapi.outbox.entity.OrderOutBoxEntity
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import org.springframework.stereotype.Repository

@Repository
class OrderOutBoxQueryRepository(
    private val springDataQueryFactory: SpringDataQueryFactory,
) {
    fun findByIdentityHashKey(identityHashKey: String): OrderOutBoxEntity = springDataQueryFactory.singleQuery {
        select(entity(OrderOutBoxEntity::class))
        from(entity(OrderOutBoxEntity::class))
        where(col(OrderOutBoxEntity::identityHashKey).equal(identityHashKey))
    }
}