package com.example.jdslexample.persistence.entity

import com.example.jdslexample.persistence.base.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "members")
class MemberEntity(
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var name: String,
    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var memberRoles: MutableList<MemberRoleEntity>
) : BaseEntity()