package com.example.webfluxr2dbc.repository

import com.example.webfluxr2dbc.entity.MemberR2Entity
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepository(
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
) {
    suspend fun findByName(name: String): List<MemberR2Entity> {
        return r2dbcEntityTemplate.select(
            Query.query(where("name").`is`(name)),
            MemberR2Entity::class.java
        ).asFlow().toList()
    }
}