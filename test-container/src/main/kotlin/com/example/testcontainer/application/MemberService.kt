package com.example.testcontainer.application

import com.example.testcontainer.persistence.MemberRepository
import com.example.testcontainer.persistence.entity.MemberEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun saveMember(name: String) {
        memberRepository.save(MemberEntity(name = name))
    }

    fun updateName(id: Long, newName: String) {
        val findById = memberRepository.findById(id).orElseThrow { IllegalStateException() }
        findById.updateName(newName)
    }
}