package com.example.kotlinmultimodule.presentation.member.dto

import com.example.kotlinmultimodule.member.domain.Member
import javax.validation.constraints.NotBlank


data class MemberRequest(
    @NotBlank
    val name: String = ""
) {
    fun toMember(): Member {
        return Member(name = this.name)
    }
}
