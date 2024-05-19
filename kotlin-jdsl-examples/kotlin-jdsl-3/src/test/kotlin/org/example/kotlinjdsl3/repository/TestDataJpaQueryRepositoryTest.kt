package org.example.kotlinjdsl3.repository

import org.assertj.core.api.Assertions.assertThat
import org.example.kotlinjdsl3.entity.TestDataJpaEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class TestDataJpaQueryRepositoryTest {
    @Autowired
    lateinit var testDataJpaRepository: TestDataJpaRepository

    @Autowired
    lateinit var testDataJpaQueryRepository: TestDataJpaQueryRepository

    private lateinit var dbData: List<TestDataJpaEntity>

    @BeforeEach
    fun init() {
        dbData = testDataJpaRepository.saveAll(
            listOf(
                TestDataJpaEntity(
                    name = "name1"
                ),
                TestDataJpaEntity(
                    name = "name2"
                ),
                TestDataJpaEntity(
                    name = "name3"
                ),
                TestDataJpaEntity(
                    name = "name4"
                )
            )
        )
    }

    @Test
    fun findByIdOrNull() {
        val id = dbData.first().id
        val data = testDataJpaQueryRepository.findByIdOrNull(id)

        assertThat(data).isNotNull
        assertThat(data!!.id).isEqualTo(id)
    }

    @Test
    fun findBySemiJoin() {
        val ids = dbData.filter { it.name == "name1" || it.name == "name3" }.map { it.id }
        val dataList = testDataJpaQueryRepository.findBySemiJoin(listOf("name1", "name3"))

        assertThat(dataList).hasSize(2)
            .extracting(TestDataJpaEntity::id)
            .isEqualTo(ids)
    }
}