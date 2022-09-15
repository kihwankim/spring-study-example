package com.example.jdslexample.persistence.repository

import com.example.jdslexample.persistence.entity.MemberEntity
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepository(
    private val springDataQueryFactory: SpringDataQueryFactory
) {

    fun findByName(name: String): List<MemberEntity> =
        springDataQueryFactory.listQuery {
            val memberEntity: EntitySpec<MemberEntity> = entity(MemberEntity::class)
            select(memberEntity)
            from(memberEntity)
            where(col(MemberEntity::name).equal(name))
        }

    fun findById(id: Long): MemberEntity? = springDataQueryFactory.listQuery<MemberEntity> {
        val memberEntity: EntitySpec<MemberEntity> = entity(MemberEntity::class)
        select(memberEntity)
        from(memberEntity)
        where(col(MemberEntity::id).equal(id))
        limit(1)
    }.firstOrNull()
}