package com.example.springrediscacheuidsetting.service

import com.example.springrediscacheuidsetting.domain.TestValue
import com.example.springrediscacheuidsetting.global.annotation.CustomCacheable
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.stereotype.Service


@Service
class TestService(
    private val redisCacheManager: RedisCacheManager,
) {

    @CustomCacheable(cacheNames = ["owaowa"], key = "#id")
    fun callValue(id: Long): TestValue {
        return TestValue(
            id = id,
            name = "newName"
        )
    }

    @CustomCacheable(cacheNames = ["owaowa"], key = "#id")
    fun callValue2(id: Long): TestValue {
        return TestValue(
            id = id,
            name = "newName"
        )
    }

    @CacheEvict(cacheNames = ["owaowa"], key = "#id")
    fun delValueByid(id: Long) {
    }

    @Cacheable(cacheNames = ["owaowa-list"], key = "'COMMON'")
    fun callValues(): List<TestValue> {
        return listOf(
            TestValue(
                id = 1L,
                name = "newName1"
            ),
            TestValue(
                id = 2L,
                name = "newName2"
            )
        )
    }

    fun saveMannual(name: String) {
        redisCacheManager.getCache("test")?.put(name, "owaowa")
    }

    fun findMannual(name: String): String? {
        return redisCacheManager.getCache("test")?.get(name, String::class.java)
    }
}