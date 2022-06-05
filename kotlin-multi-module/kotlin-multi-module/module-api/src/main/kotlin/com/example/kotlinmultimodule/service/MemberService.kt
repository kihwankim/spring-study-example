package com.example.kotlinmultimodule.service

import com.example.kotlinmultimodule.dto.MemberDto
import com.example.kotlinmultimodule.dto.MemberRequest
import com.example.kotlinmultimodule.dto.MemberResopnse
import com.example.kotlinmultimodule.infra.domain.Member
import com.example.kotlinmultimodule.infra.repository.MemberQueryRepository
import com.example.kotlinmultimodule.infra.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberQueryRepository: MemberQueryRepository
) {
    @Transactional
    fun saveMember(memberRequest: MemberRequest): MemberResopnse {
        val saveMember = memberRepository.save(Member().apply {
            this.name = memberRequest.name
        })

        val memberDto = saveMember.id?.let {
            MemberDto(
                id = it,
                name = saveMember.name
            )
        } ?: kotlin.run {
            throw IllegalArgumentException()
        }

        return MemberResopnse(memberDto)
    }
}