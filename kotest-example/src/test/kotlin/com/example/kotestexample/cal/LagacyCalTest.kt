package com.example.kotestexample.cal

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class LagacyCalTest {
    private val stub = Calculator()

    @ParameterizedTest
    @MethodSource("calculations")
    fun `식을 입력하면, 해당하는 결과값이 반환`(express: String, answer: Int) {
        val result = stub.calculate(express)

        assertThat(result).isEqualTo(answer)
    }

    companion object {
        @JvmStatic
        fun calculations() = listOf(
            Arguments { arrayOf("1 + 3 * 5", 20.0) },
            Arguments { arrayOf("2 - 8 / 3 - 3", -5.0) },
            Arguments { arrayOf("1 + 2 + 3 + 4 + 5", 15.0) }
        )
    }
}