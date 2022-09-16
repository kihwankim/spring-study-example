package com.example.jdslexample.persistence.repository

import com.example.jdslexample.persistence.entity.MemberEntity
import com.example.jdslexample.persistence.entity.MemberRoleEntity
import com.example.jdslexample.persistence.entity.RoleEntity
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.subquery
import org.springframework.stereotype.Repository
import javax.persistence.criteria.JoinType

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

    fun findByHasRoleName(roleName: String): List<MemberEntity> =
        springDataQueryFactory
            .listQuery {
                val memberEntity: EntitySpec<MemberEntity> = entity(MemberEntity::class)
                select(memberEntity)
                from(memberEntity)
                fetch(MemberEntity::memberRoles)
                fetch(MemberRoleEntity::role)
                where(col(RoleEntity::name).equal(roleName))
            }

    fun findByRoleNameSubQuery(roleNames: List<String>): List<MemberEntity> =
        springDataQueryFactory
            .listQuery {
                select(entity(MemberEntity::class))
                from(MemberRoleEntity::class)
//                where(
//                    col(MemberEntity::id).`in`(
//                        springDataQueryFactory.subquery {
//                            select(col(MemberEntity::id))
//                            from(entity(MemberRoleEntity::class))
//                            join(MemberRoleEntity::role, JoinType.INNER)
//                            where(col(RoleEntity::name).`in`(roleNames))
//                        }
//                    )
//                )
            }

}