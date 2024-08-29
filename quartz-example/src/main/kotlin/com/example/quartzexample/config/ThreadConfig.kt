package com.example.quartzexample.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.CustomizableThreadFactory
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Configuration
class ThreadConfig {
    @Bean
    fun executorService(): ExecutorService {
        return Executors.newFixedThreadPool(
            100,
            CustomizableThreadFactory("custom-executor-")
        )
    }

    @Bean
    fun schedulerFactoryBean(
        executorService: ExecutorService,
    ): SchedulerFactoryBean {
        return SchedulerFactoryBean().apply {
            setTaskExecutor(executorService)
        }
    }
}