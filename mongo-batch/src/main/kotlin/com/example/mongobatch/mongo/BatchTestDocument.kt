package com.example.mongobatch.mongo

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import kotlin.random.Random

@Document
class BatchTestDocument(
    @Id
    val id: ObjectId? = null,
    val cursorItem: Long,
    val randomId: Long,
) {
    companion object {
        fun createRandomly(): BatchTestDocument {
            return BatchTestDocument(
                cursorItem = Random.nextLong(),
                randomId = Random.nextLong(),
            )
        }
    }
}