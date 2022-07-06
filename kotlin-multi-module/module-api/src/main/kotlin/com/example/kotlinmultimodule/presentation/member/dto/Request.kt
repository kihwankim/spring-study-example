package com.example.kotlinmultimodule.presentation.member.dto

import com.example.kotlinmultimodule.member.domain.Member
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty


data class MemberRequest(
    @NotBlank
    @NotEmpty
    val name: String = ""
) {
    fun toMember(): Member {
        return Member(name = this.name)
    }
}
