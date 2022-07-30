package com.example.kotlinmultimodule.adapter.member.repository

import com.example.kotlinmultimodule.adapter.member.entity.MemberEntity
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import org.springframework.stereotype.Repository

@Repository
internal class MemberQueryRepositoryImpl(
    private val springDataQueryFactory: SpringDataQueryFactory
) : MemberQueryRepository {
    override fun findByName(name: String): List<MemberEntity> =
        springDataQueryFactory.listQuery {
            val memberEntity: EntitySpec<MemberEntity> = entity(MemberEntity::class)
            select(memberEntity)
            from(memberEntity)
            where(col(MemberEntity::name).equal(name))
        }

    override fun findById(memberId: Long): MemberEntity = springDataQueryFactory.singleQuery {
        select(entity(MemberEntity::class))
        from(MemberEntity::class)
        where(col(MemberEntity::id).equal(memberId))
    }
}