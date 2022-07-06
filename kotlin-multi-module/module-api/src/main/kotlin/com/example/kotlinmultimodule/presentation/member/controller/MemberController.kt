package com.example.kotlinmultimodule.presentation.member.controller

import com.example.kotlinmultimodule.member.domain.port.`in`.MemberCreationUseCase
import com.example.kotlinmultimodule.member.domain.port.`in`.MemberSearchUseCase
import com.example.kotlinmultimodule.presentation.member.dto.MemberRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import javax.validation.Valid

@RestController
class MemberController(
    private val memberCreateUseCase: MemberCreationUseCase,
    private val memberSearchUseCase: MemberSearchUseCase,
) {
    @PostMapping("/api/v1/members")
    fun healthChecker(@Valid @RequestBody memberRequest: MemberRequest): ResponseEntity<URI> {
        val saveMember = memberCreateUseCase.createMember(memberRequest.toMember())

        val userCreateUri: URI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saveMember.memberId)
            .toUri()

        return ResponseEntity.created(userCreateUri).build()
    }
}