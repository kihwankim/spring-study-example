package com.example.hystrixfeign.application

import com.example.hystrixfeign.adapater.CallApiClient
import mu.KotlinLogging
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }


@Component
class CirtcuitFactoryWayService(
    private val callApiClient: CallApiClient,
    private val circuitBreakerFactory: CircuitBreakerFactory<*, *>
) : ApiService {

    override fun callNoraml() {
        circuitBreakerFactory.create(CIRCUIT_NAME)
            .run(
                { callApiClient.calledData() },
                { handleNormal() }
            )
    }

    fun handleNormal() {
        logger.info { "handle normal" }
    }

    override fun callTimeout(): String {
        return circuitBreakerFactory.create(CIRCUIT_NAME)
            .run(
                { callApiClient.calledTimeOut() },
                { handleTimeout() }
            )
    }

    fun handleTimeout(): String {
        logger.info { "Timeout Handle" }
        return "Timeout Handle"
    }

    override fun callNotFound(): String {
        return circuitBreakerFactory.create(CIRCUIT_NAME)
            .run(
                { callApiClient.callFiled() },
                { handleNotFound() }
            )
    }

    fun handleNotFound(): String {
        logger.info { "recover not found" }
        return "recover not found"
    }
}