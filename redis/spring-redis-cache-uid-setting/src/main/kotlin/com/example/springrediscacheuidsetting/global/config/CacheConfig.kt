package com.example.springrediscacheuidsetting.global.config

import com.example.springrediscacheuidsetting.global.constants.BASE_PACKAGE
import com.example.springrediscacheuidsetting.util.ReflectionUtil
import org.redisson.spring.data.connection.RedissonConnectionFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.*
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import java.io.ObjectStreamClass
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.time.Duration

@Configuration
@EnableCaching
class CacheConfig(
    @Value("\${spring.profiles.active}") private val phase: String,
    private val applicationContext: ApplicationContext,
    private val redissonConnectionFactory: RedissonConnectionFactory,
) : CachingConfigurerSupport() {

    companion object {
        private val log = LoggerFactory.getLogger(CacheConfig::class.java)

        private fun fetchReturnTypeIfIterableFromMethod(method: Method): Class<*> {
            return when {
                Iterable::class.java.isAssignableFrom(method.returnType) -> (method.genericReturnType as ParameterizedType).actualTypeArguments.first() as Class<*>
                else -> method.returnType
            }
        }
    }

    override fun cacheResolver(): CacheResolver {
        return CacheUIDPostfixResolver(redisCacheManager())
    }

    @Bean
    @Primary
    fun redisCacheManager(): RedisCacheManager {
        return RedisCacheManager.builder(redissonConnectionFactory)
            .initialCacheNames(generateCacheNames())
            .cacheDefaults(redisDefaultConfig().entryTtl(Duration.ofSeconds(60)))
            .transactionAware()
            .enableStatistics()
            .build()
    }

    private fun redisDefaultConfig(): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
            .disableCachingNullValues()
            .prefixCacheNameWith("$phase-")
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(JdkSerializationRedisSerializer(applicationContext.classLoader)))
    }

    private fun generateCacheNames(userPhase: Boolean = true): Set<String> {
        return listOf(Cacheable::class.java)
            .flatMap { annotationType ->
                ReflectionUtil.getAnnotationMethod(BASE_PACKAGE, annotationType)
                    .map { method -> fetchCacheNameFromCacheable(userPhase, annotationType, method) }
                    .also { log.info("${annotationType.simpleName} cache name registered count: ${it.count()}, usePhase:$userPhase, cacheList: $it}") }
            }.toSet()
    }

    private fun fetchCacheNameFromCacheable(userPhase: Boolean, annotationType: Class<*>, method: Method): String {
        val cacheName = when (annotationType) {
            Cacheable::class.java -> method.getAnnotation(annotationType).cacheNames.first()
            else -> throw IllegalArgumentException()
        }
        if (cacheName.isBlank()) throw IllegalArgumentException()

        return when (userPhase) {
            true -> "$phase-$cacheName-${ObjectStreamClass.lookup(fetchReturnTypeIfIterableFromMethod(method)).serialVersionUID}"
            false -> cacheName
        }
    }

    override fun errorHandler(): CacheErrorHandler = object : SimpleCacheErrorHandler() {
        override fun handleCacheGetError(exception: RuntimeException, cache: Cache, key: Any) {
            log.error("error get")
        }

        override fun handleCachePutError(exception: RuntimeException, cache: Cache, key: Any, value: Any?) {
            log.error("error put")
        }

        override fun handleCacheEvictError(exception: RuntimeException, cache: Cache, key: Any) {
            log.error("error evict")
        }

        override fun handleCacheClearError(exception: RuntimeException, cache: Cache) {
            log.error("error clear")
        }
    }


    class CacheUIDPostfixResolver(
        cacheManager: RedisCacheManager,
    ) : SimpleCacheResolver(cacheManager) {

        override fun getCacheNames(context: CacheOperationInvocationContext<*>): MutableCollection<String> {
            val names = super.getCacheNames(context)

            val method = context.method

            val returnType = fetchReturnTypeIfIterableFromMethod(method)

            return names.map { "$it-${ObjectStreamClass.lookup(returnType).serialVersionUID}" }.toMutableList()
        }
    }
}