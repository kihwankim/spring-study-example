package com.example.javaspringcloudcontract.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.example.javaspringcloudcontract"})
public class FeignClientConfig {
}
