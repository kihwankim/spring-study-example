package com.example.springrediscacheuidsetting.global.annotation

import org.intellij.lang.annotations.Language
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.annotation.AliasFor
import java.lang.annotation.*
import kotlin.reflect.KClass


@Cacheable
@Inherited
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CustomCacheable(
    @get:AliasFor(annotation = Cacheable::class) val value: Array<String> = [],
    @get:AliasFor(annotation = Cacheable::class) val cacheNames: Array<String> = [],
    @get:AliasFor(annotation = Cacheable::class) val keyGenerator: String = "",
    @get:AliasFor(annotation = Cacheable::class) val cacheManager: String = "",
    @get:AliasFor(annotation = Cacheable::class) val cacheResolver: String = "",
    @get:AliasFor(annotation = Cacheable::class) val condition: String = "",
    @get:AliasFor(annotation = Cacheable::class) val unless: String = "",
    @get:AliasFor(annotation = Cacheable::class) val sync: Boolean = false,
    @get:AliasFor(annotation = Cacheable::class)
    @get:Language("SpEL")
    val key: String = "",
    val serialTypes: Array<KClass<*>> = [],
)
