package com.example.webfluxr2dbc.controller

import com.example.webfluxr2dbc.command.MemberCreateCommand

data class MemberCreateRequest(
    val name: String?,
    val roleId: Long?,
) {
    fun toCommand(): MemberCreateCommand = MemberCreateCommand(
        name!!,
        roleId!!
    )
}