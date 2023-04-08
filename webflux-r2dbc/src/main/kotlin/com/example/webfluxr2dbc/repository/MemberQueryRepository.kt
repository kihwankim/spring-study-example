package com.example.webfluxr2dbc.repository

import com.example.webfluxr2dbc.entity.MemberR2Entity
import com.example.webfluxr2dbc.supports.r2dbc.helper.column.col
import com.example.webfluxr2dbc.supports.r2dbc.helper.fetcher.select
import com.example.webfluxr2dbc.supports.r2dbc.helper.where.query
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepository(
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
) {
    suspend fun findByName(name: String?): List<MemberR2Entity> {
        var opt: Long? = null
        if (name == "owa") {
            opt = 1L
        }

        return r2dbcEntityTemplate.select(MemberR2Entity::class)
            .matching(
                query(
                    name?.run { where(col(MemberR2Entity::name)).`is`(name) },
                    opt?.run { where(col(MemberR2Entity::roleId)).`is`(opt) }
                )
            )
            .all().asFlow().toList()
    }
}