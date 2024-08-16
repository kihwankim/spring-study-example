package org.example.mongodecimelexample.mongo

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document("testData")
class TestDataMongo(
    val id: ObjectId? = null,
    val price: BigDecimal,
)