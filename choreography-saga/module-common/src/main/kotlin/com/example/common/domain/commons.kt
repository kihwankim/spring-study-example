package com.example.common.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

val objectMapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())