package com.example.webfluxr2dbc.supports.strings

private val upperPattern = "(?<=.)[A-Z]".toRegex()

fun String.toSnakeCase(): String = this.replace(upperPattern, "_$0").lowercase()
