package com.example.jdslexample.persistence.entity

import com.example.jdslexample.persistence.base.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "member_role")
class MemberRoleEntity(
    @Id
    @Column(name = "member_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var member: MemberEntity,
    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var role: RoleEntity,
) : BaseEntity()