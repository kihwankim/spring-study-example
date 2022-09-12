package com.example.kotlinmultimodule.member.domain.port.`in`

import com.example.kotlinmultimodule.member.domain.model.Member

interface MemberCreationUseCase {
    fun createMember(member: Member): Member
}