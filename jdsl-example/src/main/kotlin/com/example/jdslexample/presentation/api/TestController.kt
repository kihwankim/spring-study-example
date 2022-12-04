package com.example.jdslexample.presentation.api

import com.example.jdslexample.persistence.repository.MemberQueryRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    val memberQueryRepository: MemberQueryRepository
) {

    @GetMapping("/test")
    fun test(): ResponseEntity<String> {
        memberQueryRepository.queryUtilTest()
        return ResponseEntity.ok("1234")
    }
}