package com.example.jdslexample.persistence.repository

import com.example.jdslexample.common.init.TestMemberInit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class MemberQueryRepositoryTest {

    @Autowired
    lateinit var testMemberInit: TestMemberInit

    @Autowired
    lateinit var memberQueryRepository: MemberQueryRepository

    @BeforeEach
    fun setup() {
        testMemberInit.initAll()
    }

    @Test
    fun findByName() {
        val findByNameList = memberQueryRepository.findByName("test1")

        assertThat(findByNameList).hasSize(1)
    }

    @Test
    fun findByHasRoleName() {
        val result = memberQueryRepository.findByHasRoleName("user")
        assertThat(result).hasSize(1)
        assertThat(result[0].memberRoles).hasSize(1)
        assertThat(result[0].memberRoles[0].role.name).isEqualTo("user")
    }

    @Test
    @Throws(Exception::class)
    fun `subquery test`() {
        val result = memberQueryRepository.findByRoleNameSubQuery(listOf("admin", "user"))
        assertThat(result).hasSize(2)
    }
}