package com.example.mongobatch.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class BatchConfig {
    @Primary
    @Bean
    fun batchTransactionManager(
        batchDataSource: DataSource
    ): PlatformTransactionManager {
        return JdbcTransactionManager(batchDataSource)
    }
}