package org.example.feignclientexample.infra.clients

import org.example.feignclientexample.web.dto.ResponseData
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "\${clients.local.name}", url = "\${clients.local.url}")
interface LocalFeignClient {

    @GetMapping("/call-for-remote")
    fun getRemote(@RequestParam("id") id: Long, @RequestParam("name") name: String): ResponseData
}