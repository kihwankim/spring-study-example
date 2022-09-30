package com.example.kotestexample.cal

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class CalSpringBootBehavioWithMockSpec : BehaviorSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var calculatorService: CalculatorService

    @MockkBean
    private lateinit var mockComponent: MockComponent

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

        this.Given("Mocking 한 값과 합을 구한다") {
            every { mockComponent.returnOne() } answers { 2 }

            When("덧셈 로직 실행") {
                val result = calculatorService.calPlus(2)
                Then("덧셈 결과") {
                    result shouldBe 4
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
