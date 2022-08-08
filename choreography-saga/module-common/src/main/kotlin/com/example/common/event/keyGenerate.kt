package com.example.common.event

import java.time.ZoneId

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom

private const val ZONE_ID = "Asia/Seoul"
private const val DATE_FORMAT_PATTERN = "yyyyMMddHHmmssSSS"

private const val ORIGIN: Long = 1000000
private const val BOUND: Long = 9999999

private fun generate(): String {
    val bounded: Long = ThreadLocalRandom.current().nextLong(ORIGIN, BOUND)
    val now = ZonedDateTime.now(ZoneId.of(ZONE_ID))
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)

    return formatter.format(now) + bounded
}

fun keyGenerate(prefix: String): String {
    return prefix + generate()
}