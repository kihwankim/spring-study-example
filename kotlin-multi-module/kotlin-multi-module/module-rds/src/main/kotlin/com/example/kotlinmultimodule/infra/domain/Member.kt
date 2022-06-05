package com.example.kotlinmultimodule.infra.domain

import javax.persistence.*

@Entity
data class Member(
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String = ""
) : BaseEntity()
