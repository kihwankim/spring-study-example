package com.example.jdslexample.domain.application

import com.example.jdslexample.persistence.repository.MemberQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberQueryRepository: MemberQueryRepository,
) {

    fun findByName(name: String): List<Long> = memberQueryRepository.findByName(name).map { it.id }
}