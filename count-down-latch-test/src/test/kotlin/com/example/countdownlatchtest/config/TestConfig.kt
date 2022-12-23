package com.example.countdownlatchtest.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.support.TransactionTemplate

@Configuration
class TestConfig {
    @Bean
    fun transactionTemplate(
        platformTransactionManager: PlatformTransactionManager
    ): TransactionTemplate {
        val template = TransactionTemplate()
        template.transactionManager = platformTransactionManager
        template.isolationLevel = TransactionDefinition.ISOLATION_DEFAULT
        template.propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRED
        template.timeout = TransactionDefinition.TIMEOUT_DEFAULT
        return template
    }
}