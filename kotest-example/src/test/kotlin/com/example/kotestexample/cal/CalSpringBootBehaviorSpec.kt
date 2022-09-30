package com.example.kotestexample.cal

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class CalSpringBootBehaviorSpec : BehaviorSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var calculatorService: CalculatorService

    init {
        this.Given("calculate") {
            When("식이 주어지면") {
                Then("해당 식에 대한 결과값이 반환된다") {
                    calculations.forAll { (expression, data) ->
                        val result = calculatorService.calculate(expression)

                        result shouldBe data
                    }
                }
            }
        }
    }

    companion object {
        private val calculations = listOf(
            "1 + 3 * 5" to 20.0,
            "2 - 8 / 3 - 3" to -5.0,
            "1 + 2 + 3 + 4 + 5" to 15.0
        )
    }
}
