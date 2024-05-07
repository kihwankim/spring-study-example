package org.example.feignclientexample.controller

import org.example.feignclientexample.client.LocalFeignClient
import org.example.feignclientexample.controller.dto.ResponseData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController(
    private val localFeignClient: LocalFeignClient,
) {
    @GetMapping("/call-for-remote")
    fun callDataForRemote(@RequestParam("id") id: Long, @RequestParam("name") name: String): ResponseData {
        return ResponseData(
            id = id,
            name = name
        )
    }

    @GetMapping("/call")
    fun callDataInternal(@RequestParam("id") id: Long, @RequestParam("name") name: String) = localFeignClient.getRemote(id, name)
}