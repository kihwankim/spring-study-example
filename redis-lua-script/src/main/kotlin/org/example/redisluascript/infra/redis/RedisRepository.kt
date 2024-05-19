package org.example.redisluascript.infra.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun putAllLuaScript(key: String, values: Map<String, String>, isNew: Boolean) {
        val scriptInfo = LuaScriptCreator.hmsetAll(key, values, isNew, Duration.ofSeconds(30L))
        redisTemplate.execute(
            scriptInfo.script,
            scriptInfo.keys,
            *scriptInfo.values
        )
    }
}