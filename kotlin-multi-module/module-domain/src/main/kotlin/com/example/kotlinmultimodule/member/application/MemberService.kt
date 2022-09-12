package com.example.kotlinmultimodule.member.application

import com.example.kotlinmultimodule.member.domain.model.Member
import com.example.kotlinmultimodule.member.domain.port.`in`.MemberCreationUseCase
import com.example.kotlinmultimodule.member.domain.port.`in`.MemberSearchUseCase
import com.example.kotlinmultimodule.member.domain.port.out.MemberSavePort
import com.example.kotlinmultimodule.member.domain.port.out.MemberSearchPort

class MemberService(
    private val memberSavePort: MemberSavePort,
    private val memberSearchPort: MemberSearchPort,
) : MemberCreationUseCase, MemberSearchUseCase {
    override fun createMember(member: Member): Member {
        return memberSavePort.createMember(member)
    }

    override fun findMember(memberId: Long): Member {
        return memberSearchPort.findMember(memberId)
    }
}