package org.example.callerweb.controller

import mu.KotlinLogging
import org.example.callerweb.client.CalleeRestClient
import org.example.callerweb.client.CalleeRestTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriBuilderFactory

@RestController
class TestController(
    private val calleeRestTemplate: CalleeRestTemplate,
    private val calleeRestClient: CalleeRestClient,
    private val localUriBuilderFactory: UriBuilderFactory,
) {

    companion object {
        private val log = KotlinLogging.logger { }
    }

    @GetMapping("/call")
    fun callData(): String {
        log.info("!!!call!!!!")
        return calleeRestTemplate.calleeCall(localUriBuilderFactory).also { log.info("!!!! call finished") }
    }

    @GetMapping("/error")
    fun callError(): String {
        log.info("!!!call Error!!!!")
        return calleeRestClient.calleeError(localUriBuilderFactory).also { log.info("!!!! call error finished") }
    }


    @GetMapping("/call-client")
    fun callDataClient(): String {
        log.info("!!!call!!!!")
        return calleeRestClient.calleeCall(localUriBuilderFactory).also { log.info("!!!! call finished") }
    }

    @GetMapping("/error-client")
    fun callErrorClient(): String {
        log.info("!!!call Error!!!!")
        return calleeRestTemplate.calleeError(localUriBuilderFactory).also { log.info("!!!! call error finished") }
    }
}