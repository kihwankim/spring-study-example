package com.example.kotlinmultimodule.service

import com.example.kotlinmultimodule.infra.domain.Member
import com.example.kotlinmultimodule.infra.repository.MemberQueryRepository
import com.example.kotlinmultimodule.infra.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberQueryRepository: MemberQueryRepository
) {
    fun saveMember() {
        val member = Member().apply {
            this.name = "kkh"
        }
//        memberRepository.save(member)
    }
}