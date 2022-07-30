package com.example.kotlinmultimodule.adapter.member

import com.example.kotlinmultimodule.adapter.member.entity.MemberEntity
import com.example.kotlinmultimodule.adapter.member.repository.MemberQueryRepository
import com.example.kotlinmultimodule.adapter.member.repository.MemberRepository
import com.example.kotlinmultimodule.member.domain.Member
import com.example.kotlinmultimodule.member.domain.port.out.MemberSavePort
import com.example.kotlinmultimodule.member.domain.port.out.MemberSearchPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
internal class MemberSavePersistenceAdapter(
    private val memberRepository: MemberRepository,
    private val memberQueryRepository: MemberQueryRepository
) : MemberSavePort, MemberSearchPort {

    @Transactional
    override fun createMember(member: Member): Member {
        val saveCandidateMember = MemberEntity(name = member.name)
        val savedMember = memberRepository.save(saveCandidateMember)

        return Member(savedMember.id, savedMember.name)
    }

    override fun findMember(memberId: Long): Member {
        val member = memberQueryRepository.findById(memberId)

        return Member(memberId = member.id, name = member.name)
    }
}