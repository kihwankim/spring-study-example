package com.example.redisqueueexample

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate
import tools.jackson.databind.ObjectMapper

@SpringBootTest(properties = ["app.redis-stream.enabled=false"])
class RedisQueueExampleApplicationTests {
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var redisTemplate: StringRedisTemplate

    @Test
    fun contextLoads() {
    }
}
