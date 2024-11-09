package com.example.springdatajparelativeexample.common.persistence.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    lateinit var createTime: LocalDateTime

    @LastModifiedDate
    @Column(nullable = false)
    lateinit var updateTime: LocalDateTime
        protected set

    internal fun updateNew() {
        this.updateTime = LocalDateTime.now()
    }
}
