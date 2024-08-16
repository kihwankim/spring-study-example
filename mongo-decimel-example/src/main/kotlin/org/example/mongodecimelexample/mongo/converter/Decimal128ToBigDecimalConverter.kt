package org.example.mongodecimelexample.mongo.converter

import org.bson.types.Decimal128
import org.springframework.core.convert.converter.Converter
import java.math.BigDecimal

class Decimal128ToBigDecimalConverter : Converter<Decimal128, BigDecimal?> {
    override fun convert(source: Decimal128): BigDecimal? {
        return source.bigDecimalValue()
    }
}