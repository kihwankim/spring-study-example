package org.example.kotlinjdsl3.global.extensions

fun <T : Any> List<T?>.toNotNull(): List<T> = this.map {
    it ?: throw IllegalArgumentException("Collection has null value.")
}