package com.example.orderapi.item.repository

import com.example.orderapi.item.entity.ItemEntity
import com.example.orderapi.item.entity.ItemUpdateLockStatus
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.updateQuery
import org.springframework.stereotype.Repository

@Repository
internal class ItemQueryRepository(
    private val springDataQueryFactory: SpringDataQueryFactory
) {
    fun findByItemIdIn(itemIds: List<Long>): List<ItemEntity> = springDataQueryFactory.listQuery {
        val entity: EntitySpec<ItemEntity> = entity(ItemEntity::class)
        select(entity)
        from(entity)
        where(col(ItemEntity::id).`in`(itemIds))
    }

    fun updateStatus(itemUpdateLockStatus: ItemUpdateLockStatus, itemIds: List<Long>) {
        springDataQueryFactory.updateQuery<ItemEntity> {
            set(col(ItemEntity::status), itemUpdateLockStatus)
            where(col(ItemEntity::id).`in`(itemIds))
        }
    }
}