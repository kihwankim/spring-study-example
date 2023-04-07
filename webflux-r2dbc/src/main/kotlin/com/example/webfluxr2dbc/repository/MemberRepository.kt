package com.example.webfluxr2dbc.repository

import com.example.webfluxr2dbc.entity.MemberR2Entity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface MemberRepository : ReactiveCrudRepository<MemberR2Entity, Long> {
    fun findByName(name: String): Flux<MemberR2Entity>
}