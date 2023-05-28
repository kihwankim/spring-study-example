package com.example.springrediscacheuidsetting.global.config

import io.netty.resolver.AddressResolver
import io.netty.resolver.AddressResolverGroup
import io.netty.resolver.DefaultNameResolver
import io.netty.util.concurrent.EventExecutor
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.api.RedissonReactiveClient
import org.redisson.client.codec.StringCodec
import org.redisson.config.Config
import org.redisson.connection.AddressResolverGroupFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetSocketAddress

@Configuration
class RedissionConfig(
    @Value("\${spring.redis.host}") private val redisHost: String,
    @Value("\${spring.redis.port}") private val redisPort: String,
) {

    @Bean
    fun redissonClient(): RedissonClient = Redisson.create(createRedissonConfig())

    @Bean
    fun redissonReactiveClient(): RedissonReactiveClient = redissonClient().reactive()

    private fun createRedissonConfig(): Config {
        return Config().also { config ->
            config.codec = StringCodec(Charsets.UTF_8)
            config.useSingleServer().apply {
                address = "redis://$redisHost:$redisPort"
                connectionMinimumIdleSize = 10
                connectionPoolSize = 30
                connectTimeout = 5000
                timeout = 3000
                idleConnectionTimeout = 3000
            }

            config.addressResolverGroupFactory = AddressResolverGroupFactory { _, _ ->
                object : AddressResolverGroup<InetSocketAddress>() {
                    override fun newResolver(executor: EventExecutor?): AddressResolver<InetSocketAddress> = DefaultNameResolver(executor).asAddressResolver()
                }
            }
        }
    }
}