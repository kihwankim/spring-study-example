package com.example.webfluxr2dbc.supports.r2dbc.helper.where

import com.example.webfluxr2dbc.supports.strings.toSnakeCase
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import kotlin.reflect.KProperty

fun query(vararg args: Criteria?): Query = Query.query(
    args.fold(Criteria.empty()) { acc, arg ->
        arg?.let { acc.and(it) } ?: acc
    }
)

fun <T : Any> where(property: KProperty<T>): Criteria.CriteriaStep = Criteria.where(property.name.toSnakeCase())