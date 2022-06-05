package com.example.kotlinmultimodule.service

import com.example.kotlinmultimodule.dto.MemberRequest
import com.example.kotlinmultimodule.dto.MemberResopnse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Test
    fun `Member 저장 기능 테스트`() {
        // given
        val memberRequest = MemberRequest(name = "kkh")

        // when
        val saveMember: MemberResopnse = memberService.saveMember(memberRequest)

        // then
        assertThat(saveMember.member.name).isEqualTo("kkh")
    }
}