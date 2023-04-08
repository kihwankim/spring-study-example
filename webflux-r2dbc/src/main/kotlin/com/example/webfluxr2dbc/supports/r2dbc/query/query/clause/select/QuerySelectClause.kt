package com.example.webfluxr2dbc.supports.r2dbc.query.query.clause.select

interface QuerySelectClause<T> {
    val returnType: Class<T>

    fun apply()
}