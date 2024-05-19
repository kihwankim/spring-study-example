package org.example.redisluascript.infra.redis

import org.springframework.data.redis.core.script.RedisScript

data class LuaScriptInfo<T>(
    val script: RedisScript<T>,
    val keys: List<String>,
    val values: Array<*>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LuaScriptInfo<*>) return false

        if (script != other.script) return false
        if (keys != other.keys) return false
        if (!values.contentEquals(other.values)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = script.hashCode()
        result = 31 * result + keys.hashCode()
        result = 31 * result + values.contentHashCode()
        return result
    }
}
