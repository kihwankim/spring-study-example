package org.example.mongodecimelexample.config

import org.example.mongodecimelexample.mongo.converter.BigDecimalToDecimal128Converter
import org.example.mongodecimelexample.mongo.converter.Decimal128ToBigDecimalConverter
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions.MongoConverterConfigurationAdapter

@Configuration
class MongoConfig : AbstractMongoClientConfiguration() {
    override fun getDatabaseName(): String {
        return "test"
    }

    override fun configureConverters(converterConfigurationAdapter: MongoConverterConfigurationAdapter) {
        converterConfigurationAdapter.registerConverters(
            listOf(
                BigDecimalToDecimal128Converter(),
                Decimal128ToBigDecimalConverter()
            )
        )
    }
}