package org.example.kotlinjdsl3.repository

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities.entity
import org.example.kotlinjdsl3.entity.TestDataJpaEntity
import org.example.kotlinjdsl3.global.extensions.toNotNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class TestDataJpaQueryRepository(
    private val kotlinJdslJpqlExecutor: TestDataJpaRepository,
) {
    fun findByIdOrNull(id: Long): TestDataJpaEntity? {
        val entity = entity(TestDataJpaEntity::class)
        return kotlinJdslJpqlExecutor.findAll {
            select(entity)
                .from(entity)
                .where(path(TestDataJpaEntity::id).equal(id))
        }.firstOrNull()
    }

    fun findBySemiJoin(names: List<String>): List<TestDataJpaEntity> {
        val entity = entity(TestDataJpaEntity::class)
        val whereEntity = entity(TestDataJpaEntity::class)
        return kotlinJdslJpqlExecutor.findAll {
            select(entity)
                .from(entity)
                .where(
                    path(TestDataJpaEntity::name).`in`(
                        select(path(TestDataJpaEntity::name))
                            .from(whereEntity)
                            .where(
                                path(TestDataJpaEntity::name).`in`(names)
                            ).asSubquery()
                    )
                )
        }.toNotNull()
    }
}