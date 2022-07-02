package com.example.kotlinmultimodule.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty


data class MemberRequest(
    @NotBlank
    @NotEmpty
    val name: String = ""
)
