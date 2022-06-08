package com.example.kotlinmultimodule.service

import com.example.kotlinmultimodule.dto.MemberRequest
import com.example.kotlinmultimodule.dto.MemberResopnse
import com.example.kotlinmultimodule.exception.MemberNotFoundException
import com.example.kotlinmultimodule.infra.domain.Member
import com.example.kotlinmultimodule.infra.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun `Member 저장 기능 테스트`() {
        // given
        val memberRequest = MemberRequest(name = "kkh")

        // when
        val saveMember: MemberResopnse = memberService.saveMember(memberRequest)

        // then
        assertThat(saveMember.member.name).isEqualTo("kkh")
    }


    @Test
    fun `Member find 성공 테스트`() {
        // given
        val member = memberRepository.save(Member(name = "name"))

        // when
        val memberResponse: MemberResopnse = member.id?.let {
            memberService.findMember(memberId = it)
        } ?: kotlin.run { throw MemberNotFoundException }

        // then
        val memberDto = memberResponse.member
        assertThat(memberDto.name).isEqualTo("name")
    }

    @Test
    fun `Member find 실패 NotFound 테스트`() {
        // given
        val id = 1L


        // when

        // then
        assertThatThrownBy { memberService.findMember(memberId = id) }.isInstanceOf(MemberNotFoundException::class.java)
    }
}