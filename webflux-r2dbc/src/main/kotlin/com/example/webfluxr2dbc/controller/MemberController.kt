package com.example.webfluxr2dbc.controller

import com.example.webfluxr2dbc.service.MemberService
import org.springframework.web.bind.annotation.*

@RestController
class MemberController(
    private val memberService: MemberService,
) {

    @PostMapping("/members")
    suspend fun saveMember(@RequestBody request: MemberCreateRequest): Long {
        return memberService.save(request.toCommand())
    }


    @GetMapping("/members/name")
    suspend fun findMember(@RequestParam name: String?) = memberService.findByName(name)

    @GetMapping("/members/name2")
    suspend fun findMember2(@RequestParam name: String) = memberService.findByNameReactive(name)
}