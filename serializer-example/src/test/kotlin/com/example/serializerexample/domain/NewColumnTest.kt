package com.example.serializerexample.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NewColumnTest : FunSpec({
    test("컬럼이 추가된 경우 테스트") {
        val data = NewColumn(id = 1L, name = "name")
        val str = Json.encodeToString(data)
        println(str)
        val serialized = """{"id":1,"name":"name"}"""

        val deserialized = Json.decodeFromString<NewColumn>(serialized)
        deserialized shouldBe data
    }
})