package com.example.coroutineaopexample.aop

import com.example.coroutineaopexample.annotation.Logging
import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.KotlinDetector
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
@Aspect
class LoggingAspect {
    companion object {
        private val FLOW_PACKAGE = Flow::class.java.name
        private val log = KotlinLogging.logger { }
    }

    @Around("@annotation(logFun)")
    fun loggingAround(joinPoint: ProceedingJoinPoint, logFun: Logging): Any? {
        val methodSignature = joinPoint.signature as MethodSignature
        return handleReactor(methodSignature, joinPoint)
    }

    private fun handleReactor(methodSignature: MethodSignature, joinPoint: ProceedingJoinPoint): Any? {
        return if (!Mono::class.java.isAssignableFrom(methodSignature.returnType) &&
            (!KotlinDetector.isSuspendingFunction(methodSignature.method) ||
                FLOW_PACKAGE == MethodParameter(methodSignature.method, -1).parameterType.name)
        ) {
            Flux.create { emitter ->
                log.info("before")
                val result = joinPoint.proceed() as Flux<*>
                result.doFinally {
                    log.info("after")
                }.subscribe { item ->
                    emitter.next(item)
                }
            }
        } else {
            Mono.defer {
                log.info("before")
                joinPoint.proceed() as Mono<*>
            }.doOnNext {
                log.info("after")
            }
        }
    }
}
