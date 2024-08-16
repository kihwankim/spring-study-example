package org.example.mongodecimelexample.mongo.converter

import org.bson.types.Decimal128
import org.springframework.core.convert.converter.Converter
import java.math.BigDecimal

class BigDecimalToDecimal128Converter : Converter<BigDecimal, Decimal128?> {
    override fun convert(source: BigDecimal): Decimal128? {
        return Decimal128(source)
    }
}