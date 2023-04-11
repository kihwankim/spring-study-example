package com.example.webfluxr2dbc.repository

import com.example.webfluxr2dbc.entity.MemberR2Entity
import com.example.webfluxr2dbc.supports.r2dbc.helper.column.column
import com.example.webfluxr2dbc.supports.r2dbc.helper.where.query
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.isEqual
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

        return r2dbcEntityTemplate.select<MemberR2Entity>()
            .matching(
                query(
                    name?.run { where(r2dbcEntityTemplate.column<MemberR2Entity, String>(MemberR2Entity::name)).isEqual(name) },
                    opt?.run { where(r2dbcEntityTemplate.column<MemberR2Entity, Long>(MemberR2Entity::roleId)).isEqual(opt) }
                )
            )
            .all().asFlow().toList()
    }
}