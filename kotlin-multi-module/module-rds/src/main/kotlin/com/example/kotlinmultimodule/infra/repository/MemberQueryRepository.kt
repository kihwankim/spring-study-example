package com.example.kotlinmultimodule.infra.repository

import com.example.kotlinmultimodule.infra.domain.Member

interface MemberQueryRepository {
    fun findByName(name: String): List<Member>
}