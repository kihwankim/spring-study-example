package org.example.feignclientexample.client

import org.example.feignclientexample.controller.dto.ResponseData
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange

@FeignClient
interface LocalFeignClient {
    @GetExchange("/call-for-remote")
    fun getRemote(@RequestParam("id") id: Long, @RequestParam("name") name: String): ResponseData
}