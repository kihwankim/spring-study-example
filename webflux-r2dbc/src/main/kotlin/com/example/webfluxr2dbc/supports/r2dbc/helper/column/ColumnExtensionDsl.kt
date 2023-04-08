package com.example.webfluxr2dbc.supports.r2dbc.helper.column

import com.example.webfluxr2dbc.supports.strings.toSnakeCase
import org.springframework.data.relational.core.mapping.Column
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

fun <C : Any> col(property: KProperty<C>): String {
    val columnAnnotation = property.javaField?.getDeclaredAnnotation(Column::class.java)
    return columnAnnotation?.value ?: property.name.toSnakeCase()
}