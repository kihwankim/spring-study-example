package com.example.serializerexample.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class MovieTest : FunSpec({

    test("객체 JSON으로 직렬화 또는 역직렬화하기") {
        val data = Movie("title", "dir", 0.1)
        val serialized = Json.encodeToString(data)

        serialized shouldBe """{"title":"title","director":"dir","rating":0.1}"""

        val deserialized = Json.decodeFromString<Movie>(serialized)
        deserialized shouldBe data
    }
})