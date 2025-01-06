package com.example.mongobatch.cursor_read_example.reader

import com.example.mongobatch.lib.batch.mongo.AbstractMongoCursorItemReader
import com.example.mongobatch.mongo.BatchTestDocument
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.lt
import org.springframework.stereotype.Component

@Component
class BulkDataCursorReader(
    mongoTemplate: MongoTemplate,
) : AbstractMongoCursorItemReader<BatchTestDocument, Long>(
    mongoTemplate = mongoTemplate,
    criteriaDefinitions = listOf(
        BatchTestDocument::randomId lt Long.MAX_VALUE,
    ),
    chunkSize = 10,
    sorts = mapOf(
        BatchTestDocument::cursorItem.name to Sort.Direction.ASC,
    ),
    type = BatchTestDocument::class,
    cursorName = BatchTestDocument::cursorItem.name,
    startCursor = 0L,
    idMapper = { item -> item.cursorItem }
)