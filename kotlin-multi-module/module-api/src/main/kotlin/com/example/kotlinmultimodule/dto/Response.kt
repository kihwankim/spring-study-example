package com.example.kotlinmultimodule.dto

data class MemberResopnse(
    val member: MemberDto
)

data class MembersResponse(
    val members: List<MemberDto>
)