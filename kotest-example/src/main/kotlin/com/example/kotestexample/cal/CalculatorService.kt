package com.example.kotestexample.cal

import org.springframework.stereotype.Service

@Service
class CalculatorService(
    private val mockComponent: MockComponent
) {
    fun calculate(expression: String): Double = Calculator().calculate(expression)

    fun calPlus(varInt: Int) = varInt + mockComponent.returnOne()
}