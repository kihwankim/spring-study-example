package com.example.testcontainer.application

import com.example.testcontainer.persistence.MemberRepository
import com.example.testcontainer.persistence.entity.MemberEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Testcontainers
internal class MemberServiceTest {

    companion object {
        @Container
        @JvmStatic
        private val container = MySQLContainer<Nothing>("mysql:5.7.37").apply {
            withDatabaseName("dev")
        }

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            container.stop()
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            container.start()
        }
    }

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    lateinit var entityManager: EntityManager

    @Test
    fun `member 저장 기능`() {
        memberService.saveMember("name")

        val findedMember = memberRepository.findByName("name")

        assertThat(findedMember.name).isEqualTo("name")
    }

    @Test
    fun `member 이름 수정`() {
        val savedMember: MemberEntity = memberRepository.save(MemberEntity(name = "name"))

        memberService.updateName(savedMember.id, "newName")
        entityManager.flush()
        entityManager.clear()

        val findById = memberRepository.findById(savedMember.id).orElseThrow()
        assertThat(findById.name).isEqualTo("newName")
    }
}