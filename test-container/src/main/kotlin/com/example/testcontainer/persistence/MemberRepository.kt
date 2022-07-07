package com.example.testcontainer.persistence

import com.example.testcontainer.persistence.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<MemberEntity, Long> {
    fun findByName(name: String): MemberEntity
}