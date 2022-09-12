package com.example.kotlinmultimodule.member.domain.port.out

import com.example.kotlinmultimodule.member.domain.model.Member

interface MemberSearchPort {
    fun findMember(memberId: Long): Member
}