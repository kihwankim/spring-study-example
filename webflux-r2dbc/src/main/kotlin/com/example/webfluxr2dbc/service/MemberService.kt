package com.example.webfluxr2dbc.service

import com.example.webfluxr2dbc.command.MemberCreateCommand
import com.example.webfluxr2dbc.entity.MemberR2Entity
import com.example.webfluxr2dbc.repository.MemberQueryRepository
import com.example.webfluxr2dbc.repository.MemberRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberQueryRepository: MemberQueryRepository,
) {

    @Transactional
    suspend fun save(command: MemberCreateCommand): Long {
        val entity = MemberR2Entity(
            name = command.name,
            roleId = command.roleId
        )

        return memberRepository.save(entity).awaitSingle().id
    }

    suspend fun findByNameReactive(name: String) = memberRepository.findByName(name).asFlow().toList()

    suspend fun findByName(name: String): List<MemberR2Entity> = memberQueryRepository.findByName(name)
}