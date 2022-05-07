package com.example.kotlinmultimodule.infra.repository

import com.example.kotlinmultimodule.infra.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>