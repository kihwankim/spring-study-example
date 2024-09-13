package org.example.redisluascript.infra.redis

import org.springframework.data.redis.core.script.DefaultRedisScript
import java.time.Duration

object LuaScriptCreator {
    private const val REDIS_SCRIPT_SAVE_DELTA_PREFIX = "local v = redis.call('exists', KEYS[1]);\n if v == 1 then "
    private const val REDIS_SCRIPT_SAVE_DELTA_POSTFIX = "return \n else return end"

    fun hmsetAll(
        redisKey: String,
        data: Map<String, String>,
        isNew: Boolean,
        ttl: Duration,
    ): LuaScriptInfo<Void> {
        var keyIndex = 1
        var valueIndex = 1
        val keys: MutableList<String> = ArrayList()
        keys.add(redisKey)

        val values: MutableList<Any> = ArrayList()
        val lua = StringBuilder("redis.call('hmset', KEYS[$keyIndex]")

        keyIndex += 1
        data.forEach { (key, value) ->
            // 레디스의 hmset 명령어는 one-based index
            lua.append(", ARGV[$valueIndex], ARGV[${valueIndex + 1}]")
            values.add(key)
            values.add(value)
            valueIndex += 2
        }
        lua.append(");\n")

        val script = DefaultRedisScript<Void>().apply {
            if (isNew) {
                lua.append("redis.call('PEXPIRE', KEYS[1], ${ttl.toMillis()});\n")
                setScriptText(lua.toString())
            } else {
                setScriptText(REDIS_SCRIPT_SAVE_DELTA_PREFIX + lua + REDIS_SCRIPT_SAVE_DELTA_POSTFIX)
            }
            setResultType(Void::class.java)
        }

        return LuaScriptInfo(
            script = script,
            keys = keys,
            values = values.toTypedArray(),
        )
    }

    private val INCRE_IF_GT_THAN_AND_EXIST = DefaultRedisScript<String>(
        """
            local key = KEYS[1]        -- 조회할 Redis 키
            local A = tonumber(ARGV[1])  -- 비교할 값 A (숫자)

            -- 해당 키가 nil인 경우
            local value = redis.call('GET', key)

            if value == false then -- nil case
                local newData = tonumber(ARGV[2]) + A
                redis.call('SET', key, newData)
                return 'save and update'
            end

            -- 조회된 값이 숫자가 아니거나 A보다 작은 경우
            local numeric_value = tonumber(value)
           
            
            -- 조회된 값이 A보다 작은 경우
            if (numeric_value + A) < 0 then
                return 'value is less than ' .. A
            else
                redis.call('INCRBY', key, A)
            end
            return 'value is valid and greater than or equal to ' .. (numeric_value + A)
        """.trimIndent(),
        String::class.java
    )

    fun increIfGtThanAndIfExist(key: String, incre: Int, default: Int): LuaScriptInfo<String> {
        return LuaScriptInfo(
            script = INCRE_IF_GT_THAN_AND_EXIST,
            keys = listOf(key),
            values = listOf(incre.toString(), default.toString()).toTypedArray(),
        )
    }
}