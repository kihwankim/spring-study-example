package com.example.springrediscacheuidsetting.domain

data class TestValue(
    val id: Long,
    val name: String,
    val title: String = "",
) : BaseDomain
