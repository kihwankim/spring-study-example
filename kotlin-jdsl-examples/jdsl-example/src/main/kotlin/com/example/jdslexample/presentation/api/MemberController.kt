package com.example.jdslexample.presentation.api

import com.example.jdslexample.domain.application.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService,
) {

    @GetMapping("/members/ids")
    fun findByIds(@RequestParam(name = "name") name: String): ResponseEntity<List<Long>> = ResponseEntity.ok(memberService.findByName(name))
}