package com.example.kotlinmultimodule.infra.repository

import com.example.kotlinmultimodule.infra.domain.Member
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepositoryImpl(
    private val springDataQueryFactory: SpringDataQueryFactory
) : MemberQueryRepository {
    override fun findByName(name: String): List<Member> =
        springDataQueryFactory.listQuery {
            val member: EntitySpec<Member> = entity(Member::class)
            select(member)
            from(member)
            where(col(Member::name).equal(name))
        }
}