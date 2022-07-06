package com.example.kotlinmultimodule.adapter.member.entity

import com.example.kotlinmultimodule.adapter.common.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "member")
data class MemberEntity(
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String = ""
) : BaseEntity()
