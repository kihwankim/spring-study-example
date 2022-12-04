package com.example.jdslexample.persistence.repository

import com.example.jdslexample.persistence.entity.MemberEntity
import com.example.jdslexample.persistence.entity.MemberRoleEntity
import com.example.jdslexample.persistence.entity.RoleEntity
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.subquery
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.*

@Repository
class MemberQueryRepository(
    private val springDataQueryFactory: SpringDataQueryFactory
) {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

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
                from(MemberEntity::class)
                where(
                    col(MemberEntity::id).`in`(
                        springDataQueryFactory.subquery {
                            val memberRoleEntity: EntitySpec<MemberRoleEntity> = entity(MemberRoleEntity::class)
                            select(nestedCol(col(MemberRoleEntity::member), MemberEntity::id))
                            from(memberRoleEntity)
                            join(memberRoleEntity, entity(RoleEntity::class), on(MemberRoleEntity::role), JoinType.INNER)
                            where(col(RoleEntity::name).`in`(roleNames))
                        }
                    )
                )
            }

    fun findByNameCriateria(name: String): List<MemberEntity> {
        // builder는 criteriaQuery, criteriaDeleteQuery ...etc(기준 쿼리) 생성을 담당 , 복합 선택(tuple root query builder), 식(fucntion -> sum, max ...etc), 술어(where 조건 정 equal ..etc), 순서(order by)
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder

        // root query 생성을 담당, subquery 생성 가능 ->  query root query select 문 생성, where 조건 절 생성 담당
        val criteriaQuery: CriteriaQuery<MemberEntity> = criteriaBuilder.createQuery(MemberEntity::class.java)

        val subquery: Subquery<MemberRoleEntity> = criteriaQuery.subquery(MemberRoleEntity::class.java) // sub query builder
        val subQueryFrom: Root<MemberRoleEntity> = subquery.from(MemberRoleEntity::class.java) // sub query from stmt
        subquery.select(subQueryFrom.get("id"))
            .where(criteriaBuilder.equal(subQueryFrom.get<Long>("id"), 1L))

        val rootMember: Root<MemberEntity> = criteriaQuery.from(MemberEntity::class.java) // from 절, 별칭에 맞는 parameter 조회 용도, 죄회 query builder의 시작점, 조건의 column 값 조회의 기준이 되는 class

        criteriaQuery.select(rootMember)
            .where(criteriaBuilder.equal(rootMember.get<String>("name"), name))

        // path -> entity class 의 그래프를 나타내는 클래스 참조된 데이터, entity 파라미터 정보를 담는 객체
        return entityManager.createQuery(criteriaQuery).resultList
    }

    fun queryUtilTest() {
        val arr: MutableList<Long> = ArrayList()
        repeat(1010) {
            arr.add(it.toLong())
        }
        println(arr)

        springDataQueryFactory.listQuery<MemberEntity> {
            val memberEntity: EntitySpec<MemberEntity> = entity(MemberEntity::class)
            select(memberEntity)
            from(memberEntity)
            where(col(MemberEntity::id).`in`(arr))
        }
    }
}