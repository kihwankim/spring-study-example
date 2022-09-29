package com.example.kotestexample.cal

enum class Operator(val operator: String, val operatorFun: (a: Double, b: Double) -> Double) {
    PLUS("+", { a, b -> a + b }),
    MINUS("-", { a, b -> a - b }),
    MULTIPLY("*", { a, b -> a * b }),
    DIVISION("/", { a, b -> a / b });

    companion object {
        fun from(operator: String) =
            values().find { it.operator == operator }
                ?: run { throw IllegalArgumentException("$operator 연산자를 찾을 수 없습니다") }
    }
}