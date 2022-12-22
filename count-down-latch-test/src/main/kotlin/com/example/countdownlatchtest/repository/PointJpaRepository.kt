package com.example.countdownlatchtest.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PointJpaRepository : JpaRepository<PointJpaEntity, Long> {
    fun findByUserIdAndPointStatus(userId: Long, pointStatus: PointJpaEntity.PointStatus): List<PointJpaEntity>
}