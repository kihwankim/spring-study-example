package com.example.orderapi.common.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
internal abstract class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    lateinit var createdTime: LocalDateTime

    @LastModifiedDate
    @Column(nullable = false)
    lateinit var updateTime: LocalDateTime
        protected set
}