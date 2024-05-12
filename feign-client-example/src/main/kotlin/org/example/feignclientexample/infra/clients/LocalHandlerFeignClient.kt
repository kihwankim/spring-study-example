package org.example.feignclientexample.infra.clients

import org.example.feignclientexample.infra.clients.handler.LocalCallBadRequestErrorDecoder
import org.example.feignclientexample.web.dto.ResponseData
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "\${clients.local.name}-handler", url = "\${clients.local.url}", configuration = [LocalCallBadRequestErrorDecoder::class])
interface LocalHandlerFeignClient {
    @GetMapping("/call-for-remote")
    fun getRemoteWithCustomExceptionHandler(
        @RequestParam("id") id: Long,
        @RequestParam("name") name: String,
    ): ResponseData
}