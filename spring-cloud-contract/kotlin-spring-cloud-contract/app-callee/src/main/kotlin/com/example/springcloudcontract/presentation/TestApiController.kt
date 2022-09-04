package com.example.springcloudcontract.presentation

import com.example.springcloudcontract.presentation.NumberType.Companion.findNumberType
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

val logger = KotlinLogging.logger { }

@RestController
class TestApiController {
    @GetMapping("/api/v1/callee")
    fun calleeFunction(@RequestParam num: Int): ResponseEntity<String> = when (findNumberType(num)) {
        NumberType.ODD -> ResponseEntity.ok("odd val")
        NumberType.EVEN -> ResponseEntity.ok("even val")
    }
}

enum class NumberType {
    ODD, EVEN;

    companion object {
        fun findNumberType(num: Int): NumberType =
            if (num % 2 == 0) {
                logger.info("odd call")
                EVEN
            } else {
                logger.info("even call")
                ODD
            }
    }
}