package com.example.webfluxr2dbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("member")
data class MemberR2Entity(
    @Id
    @Column("member_id")
    val id: Long = 0L,
    val name: String,
    val roleId: Long,
) : BaseEntity()