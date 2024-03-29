package com.example.common.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper


val objectMapper: ObjectMapper = jacksonObjectMapper()
    .registerModule(JavaTimeModule())
