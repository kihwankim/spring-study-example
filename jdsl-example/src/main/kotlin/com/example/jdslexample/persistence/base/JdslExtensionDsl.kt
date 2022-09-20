package com.example.jdslexample.persistence.base

import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import kotlin.reflect.KProperty1

fun <C, R> nestedCol(columnSpec: ColumnSpec<C>, property: KProperty1<C, R>) = NestedColumnSpec<R>(columnSpec, property.name)