package com.example.testcontainer.application

import com.example.testcontainer.persistence.MemberRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File

@Transactional
@SpringBootTest
@Testcontainers
class ComposeTest {

    companion object {
        @JvmStatic
        @Container
        private val container = DockerComposeContainer<Nothing>(
            File("src/test/resources/docker-compose.yml")
        )

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            container.start()
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            container.stop()
        }
    }

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun `member 저장 기능2`() {
        memberService.saveMember("name")
        memberService.saveMember("name2")

        val findedMember = memberRepository.findByName("name")

        Assertions.assertThat(findedMember.name).isEqualTo("name")
    }
}