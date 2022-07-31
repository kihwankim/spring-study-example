package com.example.springelexpression.config.annotation


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SpelAnnotation(
    val prefix: String,
    val spelKey: String
)
