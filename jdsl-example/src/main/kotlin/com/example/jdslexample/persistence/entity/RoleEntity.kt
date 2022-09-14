package com.example.jdslexample.persistence.entity

import com.example.jdslexample.persistence.base.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "roles")
class RoleEntity(
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var name: String
) : BaseEntity()