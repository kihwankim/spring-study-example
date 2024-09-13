package org.example.redisluascript.infra.redis

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val stringRedisTemplate: StringRedisTemplate,
) {

    companion object {
        private val log = LoggerFactory.getLogger(RedisRepository::class.java)
    }

    fun putAllLuaScript(key: String, values: Map<String, String>, isNew: Boolean) {
        val scriptInfo = LuaScriptCreator.hmsetAll(key, values, isNew, Duration.ofSeconds(30L))
        redisTemplate.execute(
            scriptInfo.script,
            scriptInfo.keys,
            *scriptInfo.values
        )
    }

    fun increIfGtDefault(key: String, quantity: Int, default: Int) {
        val scriptInfo = LuaScriptCreator.increIfGtThanAndIfExist(
            key,
            quantity,
            default
        )
        redisTemplate.execute(
            scriptInfo.script,
            scriptInfo.keys,
            *scriptInfo.values
        ).also {
            log.info("result: $it")
        }
    }

    fun increIfGtList(key: List<String>, inventories: List<Int>): List<String> {
        val scriptInfo = LuaScriptCreator.increIfGtThanAndReturnData(
            key,
            inventories
        )
        return redisTemplate.execute(
            scriptInfo.script,
            scriptInfo.keys,
            *scriptInfo.values
        ).let {
            it.map { item -> item as String }
        }
    }
}