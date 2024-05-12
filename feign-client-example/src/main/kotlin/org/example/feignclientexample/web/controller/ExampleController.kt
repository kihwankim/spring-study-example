package org.example.feignclientexample.web.controller

import mu.KotlinLogging
import org.example.feignclientexample.commons.enums.ErrorType
import org.example.feignclientexample.commons.exceptions.AppException
import org.example.feignclientexample.infra.clients.LocalFeignClient
import org.example.feignclientexample.infra.clients.LocalHandlerFeignClient
import org.example.feignclientexample.web.dto.ResponseData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController(
    private val localFeignClient: LocalFeignClient,
    private val localHandlerFeignClient: LocalHandlerFeignClient,
) {
    companion object {
        private val log = KotlinLogging.logger { }
    }

    @GetMapping("/call-for-remote")
    fun callDataForRemote(@RequestParam("id") id: Long, @RequestParam("name") name: String): ResponseData {
        return if (id % 2 == 0L) {
            log.info("call for remote")
            ResponseData(
                id = id,
                name = name
            )
        } else {
            log.error("failed call")
            throw AppException(errorType = ErrorType.BAD_REQUEST)
        }

    }

    @GetMapping("/call")
    fun callDataInternal(@RequestParam("id") id: Long, @RequestParam("name") name: String) = localFeignClient.getRemote(id, name)

    @GetMapping("/call-with-error")
    fun callWithErrorHandler(@RequestParam("id") id: Long, @RequestParam("name") name: String) = localHandlerFeignClient.getRemoteWithCustomExceptionHandler(id, name)
}