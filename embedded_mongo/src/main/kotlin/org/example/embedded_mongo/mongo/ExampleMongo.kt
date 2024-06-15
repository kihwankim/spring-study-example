package org.example.embedded_mongo.mongo

import org.bson.types.ObjectId

data class ExampleMongo(
    val id: ObjectId? = null,
    val name: String,
)