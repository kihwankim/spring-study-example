package com.example.kotlinmultimodule.adapter.member

import com.example.kotlinmultimodule.adapter.member.entity.MemberEntity
import com.example.kotlinmultimodule.adapter.member.repository.MemberRepository
import com.example.kotlinmultimodule.member.domain.Member
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
internal class MemberSavePersistenceAdapterTest {

    @Autowired
    lateinit var memberSavePersistenceAdapter: MemberSavePersistenceAdapter

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun `유저 생성 저장 - 성공`() {
        // given

        // when
        val member = Member(name = "name")

        // then
        val createMember: Member = memberSavePersistenceAdapter.createMember(member)
        assertThat(createMember.name).isEqualTo("name")
    }

    @Test
    fun `유저 조회 실패 - 실패`() {
        // given

        // when
        val id = 1L

        // then
        assertThatThrownBy { memberSavePersistenceAdapter.findMember(id) }.isInstanceOf(EmptyResultDataAccessException::class.java)
    }

    @Test
    fun `유저 단건 조회 - 성공`() {
        // given
        val entity = MemberEntity(name = "name")
        val savedMember = memberRepository.save(entity)

        // when
        val id = savedMember.id!!

        // then
        val findMember: Member = memberSavePersistenceAdapter.findMember(id)
        assertThat(findMember.memberId).isEqualTo(id)
        assertThat(findMember.name).isEqualTo("name")
    }
}