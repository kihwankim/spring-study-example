package com.example.mongobatch.libs.batch.mongo

import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.CriteriaDefinition
import org.springframework.data.mongodb.core.query.Query
import org.springframework.util.ClassUtils
import kotlin.reflect.KClass

abstract class AbstractMongoCursorItemReader<T : Any, C : Any>(
    name: String? = null,
    private val mongoTemplate: MongoTemplate,
    private var criteriaDefinitions: List<CriteriaDefinition>,
    private val chunkSize: Int,
    private val sorts: Map<String, Sort.Direction>,
    private val type: KClass<T>,
    private val cursorName: String,
    startCursor: C,
    private val idMapper: (T) -> C,
) : AbstractItemCountingItemStreamItemReader<T>() {
    private var cursor: C = startCursor
    private var result: Iterator<T> = emptyList<T>().iterator()
    private val lock = Any()

    init {
        isSaveState = false
        setName(name ?: ClassUtils.getShortName(this::class.java))
    }

    override fun doRead(): T? {
        synchronized(lock) {
            if (!this.result.hasNext()) {
                this.result = this.doCursorRead()
            }

            return if (this.result.hasNext()) {
                this.result.next().also {
                    cursor = idMapper(it)
                }
            } else {
                null
            }
        }
    }

    private fun doCursorRead(): Iterator<T> {
        val query = Query().apply {
            criteriaDefinitions.forEach {
                this.addCriteria(it)
            }
        }
        val pageRequest = PageRequest.of(
            0,
            this.chunkSize,
            convertToSort(this.sorts)
        )

        query.addCriteria(Criteria.where(cursorName).gt(cursor))
            .with(pageRequest)
        return mongoTemplate.find(
            query,
            type.java,
        ).iterator()
    }

    override fun doOpen() {}

    override fun doClose() {}

    private fun convertToSort(sorts: Map<String, Sort.Direction>): Sort {
        return Sort.by(
            sorts.entries.map { (key, direction) ->
                Sort.Order(
                    direction,
                    key,
                )
            }
        )
    }
}