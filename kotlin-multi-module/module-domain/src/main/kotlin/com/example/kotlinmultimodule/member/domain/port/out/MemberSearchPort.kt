package com.example.kotlinmultimodule.member.domain.port.out

import com.example.kotlinmultimodule.member.domain.Member

interface MemberSearchPort {
    fun findMember(memberId: Long): Member
}