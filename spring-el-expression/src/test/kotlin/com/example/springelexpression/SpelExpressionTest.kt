package com.example.springelexpression

import com.example.springelexpression.application.domain.SpelTestService
import com.example.springelexpression.application.dto.Hello
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.expression.MethodBasedEvaluationContext
import org.springframework.core.DefaultParameterNameDiscoverer
import org.springframework.core.ParameterNameDiscoverer
import org.springframework.expression.EvaluationContext
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import java.lang.reflect.Method

data class ExpressionRootObject(
    val method: Method,
    val args: Array<Any>,
    val target: Any,
    val targetClass: Class<*>
) {
    val methodName: String
        get() = method.name
}

class SpelExpressionTest {

    private lateinit var parser: ExpressionParser

    @BeforeEach
    fun setUp() {
        parser = SpelExpressionParser()
    }


    @Test
    @Throws(Exception::class)
    fun `spel Test`() {
        val exp = parser.parseExpression("new String('hello world').toUpperCase()")
        val message = exp.getValue(String::class)
        print(message)
    }

    @Test
    fun `hello 에서 파라미터 데이터 조회`() {
        val hello = Hello(1L, "contentdata")
        val context: EvaluationContext = StandardEvaluationContext(hello)
        val id = parser.parseExpression("helloId")
            .getValue(context) as Long

        assertThat(id).isEqualTo(1L)
    }

    @Test
    @Throws(Exception::class)
    fun `parameter name discovery 테스트`() {
        val hello = Hello(1L, "contentdata")
        val nameDiscoverer: ParameterNameDiscoverer = DefaultParameterNameDiscoverer()
        val runWithOneSpelEpxressionMethod: Method = SpelTestService::class.java.getMethod("runWithOneSpelEpxression", Hello::class.java)
        val root = ExpressionRootObject(
            method = runWithOneSpelEpxressionMethod,
            args = arrayOf(hello),
            target = Object(),
            targetClass = SpelTestService::class.java
        )
        val evaluationContext: EvaluationContext = MethodBasedEvaluationContext(root, runWithOneSpelEpxressionMethod, arrayOf(hello), nameDiscoverer)
        val key = parser.parseExpression("#hello.helloId")
            .getValue(evaluationContext)
            .toString()
        assertThat(key).isEqualTo("1")
    }

    @Test
    @Throws(Exception::class)
    fun `parameter name discovery 2가지 이상 테스트`() {
        val hello = Hello(1L, "contentdata")
        val nameDiscoverer: ParameterNameDiscoverer = DefaultParameterNameDiscoverer()
        val runWithOneSpelEpxressionMethod: Method = SpelTestService::class.java.getMethod("runWithOneSpelEpxression", Hello::class.java)

        val root = ExpressionRootObject(
            method = runWithOneSpelEpxressionMethod,
            args = arrayOf(hello),
            target = Object(),
            targetClass = SpelTestService::class.java
        )
        val evaluationContext: EvaluationContext = MethodBasedEvaluationContext(root, runWithOneSpelEpxressionMethod, arrayOf(hello), nameDiscoverer)
        val key = parser.parseExpression("#hello.helloId + ':' + #hello.content")
            .getValue(evaluationContext)
            .toString()
        assertThat(key).isEqualTo("1:contentdata")
    }
}

