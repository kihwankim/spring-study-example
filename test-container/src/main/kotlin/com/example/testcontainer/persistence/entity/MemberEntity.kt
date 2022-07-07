package com.example.testcontainer.persistence.entity

import javax.persistence.*

@Entity
@Table(name = "member")
data class MemberEntity(
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var name: String,
) {
    fun updateName(name: String) {
        this.name = name
    }
}
