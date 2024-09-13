package org.example.redisluascript.controller

import org.example.redisluascript.infra.redis.RedisRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val redisRepository: RedisRepository,
) {
    @PostMapping("/save")
    fun saveData(
        @RequestBody body: DataSaveRequest,
    ) {
        redisRepository.putAllLuaScript(
            body.redisKey,
            body.item.associate { it.key to it.value },
            true
        )
    }

    @GetMapping("/incre")
    fun increTest(
        @RequestParam("key") key: String,
        @RequestParam("value") value: Int,
        @RequestParam("default") default: Int,
    ) {
        redisRepository.increIfGtDefault(
            key,
            value,
            default
        )
    }
}

data class DataSaveRequest(
    val redisKey: String,
    val isNew: Boolean,
    val item: List<DataSaveItemRequest>,
)

data class DataSaveItemRequest(
    val key: String,
    val value: String,
)