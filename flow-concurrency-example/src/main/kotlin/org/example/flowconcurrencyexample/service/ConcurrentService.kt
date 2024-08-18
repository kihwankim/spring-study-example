package org.example.flowconcurrencyexample.service

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ConcurrentService(
    private val globalCustomCoroutineDispatcher: CoroutineDispatcher,
) {

    companion object {
        private val log = KotlinLogging.logger { }
        private const val CHUNK_SIZE = 10_000
        private const val TPS_SIZE = 1_000
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun runBatch(index: Int, items: List<Int>): Int {
        val startTime = System.currentTimeMillis()
        val result = items.asFlow()
            .flatMapMerge(concurrency = CHUNK_SIZE) {
                flow {
                    delay(2_500) // 1s
                    log.info("process data $it")
                    emit(it)
                }
            }.buffer(TPS_SIZE)
            .flatMapMerge(concurrency = TPS_SIZE) {
                flow {
                    delay(5_000) // 2초 씩 10번 -> 20초
                    log.info("publish data $it")
                    emit(it)
                }
            }
            .flowOn(globalCustomCoroutineDispatcher)
            .count()
        log.error("!!!! index: $index Chunk spend time ${(System.currentTimeMillis() - startTime) / 1_000} s")

        return result
    }
}