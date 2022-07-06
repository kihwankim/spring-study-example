package com.example.kotlinmultimodule.presentation.member.dto

data class MemberResopnse(
    val member: MemberPresentDto
)

data class MembersResponse(
    val members: List<MemberPresentDto>
)

data class MemberResponse(
    val member: MemberPresentDto
)