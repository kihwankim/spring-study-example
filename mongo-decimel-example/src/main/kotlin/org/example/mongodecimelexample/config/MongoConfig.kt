package org.example.mongodecimelexample.config

import org.example.mongodecimelexample.MongoDecimelExampleApplication
import org.example.mongodecimelexample.mongo.converter.BigDecimalToDecimal128Converter
import org.example.mongodecimelexample.mongo.converter.Decimal128ToBigDecimalConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.convert.MongoCustomConversions.MongoConverterConfigurationAdapter
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackageClasses = [MongoDecimelExampleApplication::class])
class MongoConfig {
    @Bean
    @Primary
    fun customConversions(): MongoCustomConversions {
        return MongoCustomConversions.create { this.configureConverters(it) }
    }

    private fun configureConverters(converterConfigurationAdapter: MongoConverterConfigurationAdapter) {
        converterConfigurationAdapter.registerConverters(
            listOf(
                BigDecimalToDecimal128Converter(),
                Decimal128ToBigDecimalConverter()
            )
        )
    }
}