package com.example.springelexpression.config.aop

import com.example.springelexpression.config.annotation.SpelAnnotation
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.expression.MethodBasedEvaluationContext
import org.springframework.core.DefaultParameterNameDiscoverer
import org.springframework.core.ParameterNameDiscoverer
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.stereotype.Component
import java.lang.reflect.Method

val logger = KotlinLogging.logger { }

@Aspect
@Component
class SpelAspect {
    companion object {
        private val expressionParser: ExpressionParser = SpelExpressionParser()
        private val nameDiscoverer: ParameterNameDiscoverer = DefaultParameterNameDiscoverer()
    }


    @Throws(Throwable::class)
    @Around("@annotation(spelAnnotation)")
    fun spelExpressionAspect(proceedingJoinPoint: ProceedingJoinPoint, spelAnnotation: SpelAnnotation) {
        val methodSignature = proceedingJoinPoint.signature as MethodSignature
        val method = methodSignature.method

        val rootObject = SpelRootObject(
            method = method,
            args = proceedingJoinPoint.args,
            target = proceedingJoinPoint.target,
            targetClass = proceedingJoinPoint.target.javaClass
        )

        logger.info { proceedingJoinPoint.args[0] }
        val evaluationContext = MethodBasedEvaluationContext(rootObject, method, proceedingJoinPoint.args, nameDiscoverer)
        val key = expressionParser.parseExpression(spelAnnotation.spelKey)
            .getValue(evaluationContext)
            .toString()
        logger.info("key: {}", key)
    }
}

internal data class SpelRootObject(
    val method: Method,
    val args: Array<Any>,
    val target: Any,
    val targetClass: Class<*>
) {
    val methodName: String
        get() = method.name
}