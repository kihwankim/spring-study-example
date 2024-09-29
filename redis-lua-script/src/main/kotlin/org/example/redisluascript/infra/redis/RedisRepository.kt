package org.example.redisluascript.infra.redis

import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.ReturnType
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer
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

    fun loopLuaScript(
        keys: List<String>,
        incrementQtyValue: List<String>,
        initQtyValue: List<String>,
    ): List<String> {
        val failedPrefix = "FAIL::"
        val scriptStr: String = """
                -- setAndIncrease.lua
                local result = {}
                for i, key in pairs(KEYS) do
                    local delta = tonumber(ARGV[i])
                    local inventory = redis.call('GET', key)
                    if inventory == false then -- nil case
                        local qty = tonumber(ARGV[#KEYS + i])
                        if (qty + delta) < 0 then
                            redis.call('SET', key, qty + delta)
                        else
                            table.insert(result, '$failedPrefix' .. key)
                        end
                    elseif (tonumber(inventory) + tonumber(ARGV[i])) < 0 then
                        table.insert(result, '$failedPrefix' .. key)
                    else
                        redis.call('INCRBY', key, delta)
                    end
                end

                return result
            """.trimIndent()

        val result = redisTemplate.execute { connection ->
            val scriptBytes: ByteArray = scriptStr.toByteArray()
            val serializedKeys = keys.map { redisTemplate.stringKeySerializer().serialize(it) }
            val serilizationValues = (incrementQtyValue + initQtyValue).map { redisTemplate.stringValueSerializer().serialize(it) }

            connection.scriptingCommands().eval<List<ByteArray>>(
                scriptBytes,
                ReturnType.MULTI,
                serializedKeys.size,
                *(serializedKeys + serilizationValues).toTypedArray()
            ).orEmpty().map { String(it) }
        }.orEmpty()
        return result.map { it }
    }

    @Suppress("UNCHECKED_CAST")
    private fun RedisTemplate<String, String>.stringKeySerializer(): RedisSerializer<String> {
        return this.keySerializer as RedisSerializer<String>
    }

    @Suppress("UNCHECKED_CAST")
    private fun RedisTemplate<String, String>.stringValueSerializer(): RedisSerializer<String> {
        return this.valueSerializer as RedisSerializer<String>
    }
}