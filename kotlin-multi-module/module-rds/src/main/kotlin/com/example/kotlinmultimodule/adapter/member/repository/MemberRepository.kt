package com.example.kotlinmultimodule.adapter.member.repository

import com.example.kotlinmultimodule.adapter.member.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<MemberEntity, Long>