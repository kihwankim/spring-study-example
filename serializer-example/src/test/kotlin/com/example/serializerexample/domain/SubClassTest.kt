package com.example.serializerexample.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SubClassTest : FunSpec({

    test("객체 JSON으로 직렬화 또는 역직렬화하기") {
        val data: Upper<Long> = SubClass(1L, "dir")
        val serialized = Json.encodeToString(data)

        serialized shouldBe """{"id":1,"name":"dir"}"""

        val deserialized = Json.decodeFromString<SubClass>(serialized)
        deserialized shouldBe data
    }
})