package org.example.embedded_mongo.mongo

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ExampleMongoRepository : MongoRepository<ExampleMongo, ObjectId> {
    fun findByName(name: String): ExampleMongo?
}
