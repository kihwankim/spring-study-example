package com.example.webfluxr2dbc.supports.r2dbc.helper.fetcher

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.ReactiveSelectOperation
import kotlin.reflect.KClass

fun <T : Any> R2dbcEntityTemplate.select(type: KClass<T>): ReactiveSelectOperation.ReactiveSelect<T> = this.select(type.java)