package com.example.kotlinmultimodule.controller

import com.example.kotlinmultimodule.dto.MemberRequest
import com.example.kotlinmultimodule.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import javax.validation.Valid

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/api/v1/members")
    fun healthChecker(@Valid @RequestBody memberRequest: MemberRequest): ResponseEntity<URI> {
        val saveMember = memberService.saveMember(memberRequest)

        val userCreateUri: URI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saveMember.member.id)
            .toUri()

        return ResponseEntity.created(userCreateUri).build()
    }
}