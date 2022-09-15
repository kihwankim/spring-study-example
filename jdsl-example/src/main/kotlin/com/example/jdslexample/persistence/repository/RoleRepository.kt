package com.example.jdslexample.persistence.repository

import com.example.jdslexample.persistence.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<RoleEntity, Long>