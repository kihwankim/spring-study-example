package com.example.kotlinmultimodule.adapter.member.repository

import com.example.kotlinmultimodule.adapter.member.entity.MemberEntity

internal interface MemberQueryRepository {
    fun findByName(name: String): List<MemberEntity>

    fun findById(memberId: Long): MemberEntity
}