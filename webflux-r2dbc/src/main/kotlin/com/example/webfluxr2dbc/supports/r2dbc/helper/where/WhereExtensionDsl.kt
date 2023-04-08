package com.example.webfluxr2dbc.supports.r2dbc.helper.where

import com.example.webfluxr2dbc.supports.strings.toSnakeCase
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import kotlin.reflect.KProperty

private fun queryConditions(vararg args: Criteria?): Criteria {
    var prevCriteriaCondition = Criteria.empty()
    for (arg in args) {
        prevCriteriaCondition = arg?.let { prevCriteriaCondition.and(it) } ?: prevCriteriaCondition
    }

    return prevCriteriaCondition
}

fun query(vararg args: Criteria?): Query = Query.query(queryConditions(*args))

fun <T : Any> where(property: KProperty<T>): Criteria.CriteriaStep = Criteria.where(property.name.toSnakeCase())