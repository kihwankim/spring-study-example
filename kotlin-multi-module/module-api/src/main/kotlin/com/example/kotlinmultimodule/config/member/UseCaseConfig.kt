package com.example.kotlinmultimodule.config.member

import com.example.kotlinmultimodule.member.application.MemberService
import com.example.kotlinmultimodule.member.domain.port.out.MemberSavePort
import com.example.kotlinmultimodule.member.domain.port.out.MemberSearchPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig {

    @Bean
    fun memberService(
        memberSavePort: MemberSavePort,
        memberSearchPort: MemberSearchPort,
    ): MemberService {
        return MemberService(memberSavePort, memberSearchPort)
    }
}