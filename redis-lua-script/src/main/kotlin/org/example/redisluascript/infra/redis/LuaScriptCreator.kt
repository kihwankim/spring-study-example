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
}