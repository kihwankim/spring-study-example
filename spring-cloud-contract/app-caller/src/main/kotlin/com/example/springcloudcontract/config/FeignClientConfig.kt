package com.example.springcloudcontract.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.example.springcloudcontract"])
class FeignClientConfig
