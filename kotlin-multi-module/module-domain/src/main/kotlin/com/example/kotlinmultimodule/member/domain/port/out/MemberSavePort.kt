package com.example.kotlinmultimodule.member.domain.port.out

import com.example.kotlinmultimodule.member.domain.model.Member

interface MemberSavePort {
    fun createMember(member: Member): Member
}