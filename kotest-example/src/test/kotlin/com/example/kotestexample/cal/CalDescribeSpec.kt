package com.example.kotestexample.cal

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

class CalDescribeSpec : DescribeSpec({
    val stub = Calculator()

    describe("calculate") {
        context("식이 주어지면") {
            it("해당 식에 대한 결과 값이 반환 된다") {
                calculations.forAll { (expression, data) ->
                    val result = stub.calculate(expression)

                    result shouldBe data
                }
            }
        }

        context("0으로 나누는 경우") {
            it("infinity를 반환한다") {
                val result = stub.calculate("1 / 0")

                result shouldBe Double.POSITIVE_INFINITY
            }
        }

        context("입력 값이 null이거나 공백인 경우") {
            it("IllegalArgumentException 예외를 던진다") {
                blanks.forAll {
                    shouldThrow<IllegalArgumentException> { stub.calculate(it) }
                }
            }
        }

        context("사칙연산 기호 이외에 다른 문자가 연산자로 들어오는 경우") {
            it("illegalArgumentException 예외를 던진다") {
                invalidInputs.forAll {
                    shouldThrow<IllegalArgumentException> { stub.calculate(it) }
                }
            }
        }
    }
}) {
    companion object {
        val calculations = listOf(
            "1 + 3 * 5" to 20.0,
            "2 - 8 / 3 - 3" to -5.0,
            "1 + 2 + 3 + 4 + 5" to 15.0
        )
        val blanks = listOf("", " ", "      ")
        val invalidInputs = listOf("1 & 2", "1 + 5 % 1")
    }
}