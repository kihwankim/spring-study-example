package com.example.mongobatch.config

import com.example.mongobatch.MongoBatchApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackageClasses = [MongoBatchApplication::class])
class MongoConfig {
    @Bean
    fun mongoTransactionManager(
        mongoDatabaseFactory: MongoDatabaseFactory,
    ): MongoTransactionManager {
        return MongoTransactionManager(mongoDatabaseFactory)
    }
}