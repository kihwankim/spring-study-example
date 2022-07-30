package com.example.kotlinmultimodule.adapter.member.entity

import com.example.kotlinmultimodule.adapter.common.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "member")
internal data class MemberEntity(
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(name = "name", unique = true)
    var name: String = ""
) : BaseEntity()
