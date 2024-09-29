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

    @PostMapping("/incre-all")
    fun increTestAll(
        @RequestBody body: IncreaseRequest,
    ) = redisRepository.increIfGtList(
        body.items.map { it.key },
        body.items.map { it.value },
    )

    @PostMapping("/incre-all-loop")
    fun increTestAllLoop(
        @RequestBody body: IncreaseRequest,
    ) = redisRepository.loopLuaScript(
        keys = body.items.map { it.key },
        incrementQtyValue = body.items.map { it.value.toString() },
        initQtyValue = body.items.map { (it.init ?: 100).toString() },
    )
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

data class IncreaseRequest(
    val items: List<IncreRequestItem>,
)

data class IncreRequestItem(
    val key: String,
    val value: Int,
    val init: Int? = null,
)