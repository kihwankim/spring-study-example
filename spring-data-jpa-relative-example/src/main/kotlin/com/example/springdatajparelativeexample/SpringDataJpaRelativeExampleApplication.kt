package com.example.springdatajparelativeexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan(basePackageClasses = [SpringDataJpaRelativeExampleApplication::class])
@EnableJpaRepositories(basePackageClasses = [SpringDataJpaRelativeExampleApplication::class])
@SpringBootApplication
class SpringDataJpaRelativeExampleApplication

fun main(args: Array<String>) {
    runApplication<SpringDataJpaRelativeExampleApplication>(*args)
}
