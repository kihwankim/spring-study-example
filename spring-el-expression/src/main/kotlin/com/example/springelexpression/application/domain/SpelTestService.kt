package com.example.springelexpression.application.domain

import com.example.springelexpression.application.dto.Hello
import com.example.springelexpression.config.annotation.SpelAnnotation
import mu.KotlinLogging
import org.springframework.stereotype.Service

val log = KotlinLogging.logger {}

@Service
class SpelTestService {

    @SpelAnnotation(prefix = "hello", spelKey = "#hello.helloId + ':' + #hello.content")
    fun runWithOneSpelEpxression(hello: Hello): Hello {
        log.info("method 호출")

        return hello
    }
}