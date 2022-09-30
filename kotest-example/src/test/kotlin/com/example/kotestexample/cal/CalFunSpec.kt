package com.example.kotestexample.cal

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.forAtLeast
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveMinLength

class CalFunSpec : FunSpec({
    test("1과 2를 더하면 3이 반환된다") {
        val stub = Calculator()

        val result = stub.calculate("1 + 2")

        result shouldBe 3
    }

    test("식을 입력하면, 해당하는 결과값이 반환된다") {
        val stub = Calculator()

        calculations.forAll { (expression, answer) ->
            val result = stub.calculate(expression)

            result shouldBe answer
        }
    }

    test("입력값이 null 이거나 빈 공백 문자일 경우 IllegalArgumentException 예외를 던진다") {
        val stub = Calculator()

        blanks.forAll {
            shouldThrow<IllegalArgumentException> {
                stub.calculate(it)
            }
        }
    }

    test("사칙연산 기호 이외에 다른 문자가 연산자로 들어오는 경우 IllegalArgumentException 예외를 던진다") {
        val stub = Calculator()

        invalidInputs.forAll {
            shouldThrow<IllegalArgumentException> {
                stub.calculate(it)
            }
        }
    }

    context("enabled test run") {
        test("test code run") {
            val stub = Calculator()

            val result = stub.calculate("1 + 2")

            result shouldBe 3
        }

        xtest("test code not run") {
            val stub = Calculator()

            val result = stub.calculate("1 + 2")

            result shouldBe 3
        }
    }

    xcontext("disabled test run") {
        test("test code run but outer context is disabled") {
            val stub = Calculator()

            val result = stub.calculate("1 + 2")

            result shouldBe 3
        }

        xtest("test code not run") {
            val stub = Calculator()

            val result = stub.calculate("1 + 2")

            result shouldBe 3
        }
    }
    test("test") {
        val xs = listOf(
            "sam", // 3
            "gareth",  // 6
            "timothy", // 7
            "muhammad" // 8
        )

        xs.forAtLeast(2) {
            it.shouldHaveMinLength(7)
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