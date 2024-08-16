package org.example.mongodecimelexample.mongo

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.gt
import org.springframework.data.mongodb.repository.MongoRepository
import java.math.BigDecimal

interface TestDataMongoRepository : MongoRepository<TestDataMongo, ObjectId>, TestDataMongoRepositoryCustom

interface TestDataMongoRepositoryCustom {
    fun findByGtPrice(price: BigDecimal): List<TestDataMongo>
}

class TestDataMongoRepositoryCustomImpl(
    private val mongoTemplate: MongoTemplate,
) : TestDataMongoRepositoryCustom {
    override fun findByGtPrice(price: BigDecimal): List<TestDataMongo> {
        val query = Query().addCriteria(
            TestDataMongo::price gt price
        )
        return mongoTemplate.find<TestDataMongo>(query)
    }
}