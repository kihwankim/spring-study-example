package com.example.hystrixfeign.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.example.hystrixfeign"])
class FeignClientConfig
