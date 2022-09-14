package com.example.jdslexample.persistence.repository

import com.example.jdslexample.persistence.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<MemberEntity, Long>