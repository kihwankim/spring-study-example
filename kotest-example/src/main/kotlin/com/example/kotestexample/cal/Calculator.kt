package com.example.kotestexample.cal

class Calculator {
    fun calculate(expression: String): Double {
        require(expression.isNotBlank()) { "수식에 공백은 올 수 없습니다" }

        val terms = expression.split(" ")

        return calculate(terms)
    }

    private fun calculate(terms: List<String>): Double {
        var result = terms[0].toDoubleOrNull() ?: 0.0

        for (i in 1 until terms.size step 2) {
            val operatorSymbol = terms[i]
            val operand = terms[i + 1].toDoubleOrNull() ?: run { throw IllegalArgumentException("피연산자 데이터가 잘못되었습니다") }

            result = operate(result, operatorSymbol, operand)
        }
        return result
    }

    private fun operate(result: Double, operatorSymbol: String, operand: Double): Double =
        Operator.from(operatorSymbol).operatorFun(result, operand)
}