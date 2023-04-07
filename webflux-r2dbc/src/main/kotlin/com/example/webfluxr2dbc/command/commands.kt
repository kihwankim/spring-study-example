package com.example.webfluxr2dbc.command

data class MemberCreateCommand(
    val name: String,
    val roleId: Long,
)