package com.example.redissonlock.adapter.presentation.dto

import javax.validation.constraints.NotEmpty

data class LockRequest(
    @NotEmpty
    val lockName: String,
    val wait: Long,
    val leatTime: Long
)
