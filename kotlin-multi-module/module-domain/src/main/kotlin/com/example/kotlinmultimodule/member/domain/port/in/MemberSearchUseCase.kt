package com.example.kotlinmultimodule.member.domain.port.`in`

import com.example.kotlinmultimodule.member.domain.model.Member

interface MemberSearchUseCase {
    fun findMember(memberId: Long): Member
}