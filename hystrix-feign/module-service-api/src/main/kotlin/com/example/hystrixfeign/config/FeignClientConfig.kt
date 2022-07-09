package com.example.hystrixfeign.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration


@Configuration
@EnableFeignClients(basePackages = ["com.example.hystrixfeign"])
class FeignClientConfig {

//    @Bean
//    fun globalCustomConfiguration(): Customizer<Resilience4JCircuitBreakerFactory> {
//        val cbConfig = CircuitBreakerConfig.custom()
//            .failureRateThreshold(20.0f) // 실패 확률/횟수 -> default 50
//            .waitDurationInOpenState(Duration.ofSeconds(5)) // cirtcuit breaker가 open 후 얼마나 열어 둘것인가? -> 5초, default 1분
//            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // circuit breaker가 닫힐 때 통화 결과를 기록 하는데 사용되는 슬라이딩 창의 유형을 구성 -> count 기반
//            .slidingWindowSize(50) // circuit breaker가 닫힐 때 호출 결과를 기록하는 데 사용되는 슬라이딩 창의 크기를 구성 -> default 100
//            .build()
//
//        val timeLimiterConfig: TimeLimiterConfig = TimeLimiterConfig.custom()
//            .timeoutDuration(Duration.ofSeconds(5))
//            .build()
//
//        return Customizer { factory: Resilience4JCircuitBreakerFactory ->
//            factory.configureDefault {
//                Resilience4JConfigBuilder(it)
//                    .timeLimiterConfig(timeLimiterConfig)
//                    .circuitBreakerConfig(cbConfig)
//                    .build()
//            }
//        }
//    }

//    @Bean
//    fun circuitBreakerNameResolver(): CircuitBreakerNameResolver? {
//        return CircuitBreakerNameResolver { feignClientName: String?, target: Target<*>, method: Method? ->
//            Feign.configKey(target.type(), method)
//        }
//    }


//    resilience4j:
//  circuitbreaker:
//    configs:
//      default:
//#        registerHealthIndicator: true # actuator 정보 노출을 위한 설정 -> 현재 없음
//        ringBufferSizeInClosedState: 10 # 닫힌 상태에서의 호출 수로, 서킷을 열어야 할지 결정할 때 사용한다.
//        ringBufferSizeInHalfOpenState: 30 # 반열림 상태에서의 호출 수로, 서킷을 다시 열거나 닫힘 상태로 돌아갈지를 결정할 때 사용 한다.
//        failureRateThreshold: 80 # 실패한 호출에 대한 임계값(백분율)으로 이 값을 초과하면 서킷이 열린다.
//        waitDurationInOpenState: 5s # 열림 상태를 유지하는 시간, 해당 시간이후 반열림 상태로 변경된다.
//        slidingWindowType: COUNT_BASED
//    @Bean
//    fun defaultConfig(): Customizer<HystrixCircuitBreakerFactory?>? {
//        return Customizer<HystrixCircuitBreakerFactory> { factory ->
//            factory.configureDefault { id ->
//                HystrixCommand.Setter
//                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey(id))
//                    .andCommandPropertiesDefaults(
//                        HystrixCommandProperties.Setter()
//                            .withExecutionTimeoutInMilliseconds(20000)
//                            .withCircuitBreakerRequestVolumeThreshold(20)
//                            .withCircuitBreakerErrorThresholdPercentage(50)
//                            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
//                    )
//                    .andThreadPoolPropertiesDefaults(
//                        HystrixThreadPoolProperties.Setter()
//                            .withCoreSize(50)
//                            .withMaximumSize(300)
//                            .withAllowMaximumSizeToDivergeFromCoreSize(true)
//                    )
//            }
//        }
//    }
}
