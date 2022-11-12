package com.example.redissonlock.adapter.persistence.lock

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.util.function.Supplier

@Component
class NamingLockWithJdbc(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) {
    companion object {
        private const val GET_LOCK = "SELECT GET_LOCK(:userLockName, :timeoutSeconds)"
        private const val RELEASE_LOCK = "SELECT RELEASE_LOCK(:userLockName)"
        private const val LOCK_GETTING_NUMBER = 1
    }

    fun <T> runWithLock(lockName: String, timeoutSec: Int, supplier: Supplier<T>): T {
        try {
            getLock(lockName, timeoutSec)
            return supplier.get()
        } finally {
            releaseLock(lockName)
        }
    }

    private fun getLock(lockName: String, timeoutSec: Int) {
        val params: Map<String, Any> = mapOf(
            "userLockName" to lockName,
            "timeoutSeconds" to timeoutSec
        )

        val result: Int? = namedParameterJdbcTemplate.queryForObject(GET_LOCK, params, Int::class.java)
        verifyLockResult(result, lockName)
    }

    private fun releaseLock(lockName: String) {
        val params: Map<String, Any> = mapOf("userLockName" to lockName)
        val result: Int? = namedParameterJdbcTemplate.queryForObject(RELEASE_LOCK, params, Int::class.java)
        verifyLockResult(result, lockName)
    }

    private fun verifyLockResult(result: Int?, lockname: String) {
        if (result == null) {
            throw RuntimeException("lock 획득 실패 key: $lockname")
        }

        if (result != LOCK_GETTING_NUMBER) {
            throw RuntimeException("lock 획득 실패 key: $lockname")
        }
    }
}