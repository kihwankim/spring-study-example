package com.example.redisqueueexample.consumer

import com.example.redisqueueexample.domain.TestData
import com.example.redisqueueexample.serialization.CompressionRedisSerializer
import com.example.redisqueueexample.service.StreamLoggingService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.domain.Range
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.stereotype.Component
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import java.time.Duration
import java.util.UUID
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

@Component
@ConditionalOnProperty(prefix = "app.redis-stream", name = ["enabled"], havingValue = "true", matchIfMissing = true)
class RedisStreams(
    @Qualifier("binaryStreamRedisTemplate") private val redisTemplate: RedisTemplate<String, ByteArray>,
    private val connectionFactory: RedisConnectionFactory,
    private val serializer: CompressionRedisSerializer<TestData>,
    private val streamLoggingService: StreamLoggingService,
    @Value("\${app.redis-stream.key}") private val streamKey: String,
    @Value("\${app.redis-stream.group}") private val group: String,
) {
    internal val consumerName = "redis-queue-example-${UUID.randomUUID()}"

    private var container: StreamMessageListenerContainer<String, MapRecord<String, String, ByteArray>>? = null
    private var recoveryExecutor: ScheduledExecutorService? = null

    @PostConstruct
    fun start() {
        createGroupIfNeeded()

        val listenerContainer: StreamMessageListenerContainer<String, MapRecord<String, String, ByteArray>> = StreamMessageListenerContainer.create(
            connectionFactory,
            StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                .hashValueSerializer<String, ByteArray>(RedisSerializer.byteArray())
                .pollTimeout(Duration.ofSeconds(1))
                .build(),
        )
        val readRequest = StreamMessageListenerContainer.StreamReadRequest
            .builder(StreamOffset.create(streamKey, ReadOffset.lastConsumed()))
            .consumer(Consumer.from(group, consumerName))
            .autoAcknowledge(false)
            .cancelOnError { false }
            .errorHandler { ex ->
                log.error("Failed to read Redis Stream {}", streamKey, ex)
                try {
                    Thread.sleep(1_000)
                } catch (_: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
            .build()
        listenerContainer.register(readRequest) { record -> processRecord(record) }
        listenerContainer.start()
        container = listenerContainer

        val executor = Executors.newSingleThreadScheduledExecutor { task ->
            Thread(task, "redis-stream-pending-$consumerName").apply { isDaemon = true }
        }
        recoveryExecutor = executor
        executor.scheduleWithFixedDelay(
            {
                runCatching {
                    recoverPending()
                }.onFailure { ex ->
                    log.error("Failed to recover pending records from Redis Stream {}", streamKey, ex)

                }
            },
            0,
            RECOVERY_INTERVAL_SECONDS,
            TimeUnit.SECONDS,
        )
    }

    @PreDestroy
    fun stop() {
        recoveryExecutor?.shutdownNow()
        container?.stop()
    }

    internal fun createGroupIfNeeded() {
        try {
            redisTemplate.opsForStream<String, ByteArray>()
                .createGroup(streamKey, ReadOffset.from("0-0"), group)
        } catch (ex: Exception) {
            val groupAlreadyExists = generateSequence(ex as Throwable?) { it.cause }
                .any { it.message?.contains("BUSYGROUP", ignoreCase = true) == true }
            if (!groupAlreadyExists) throw ex
        }
    }

    fun processRecord(record: MapRecord<String, String, ByteArray>) {
        runCatching {
            val payload = requireNotNull(record.value["data"]) { "Missing data field" }
            val data = requireNotNull(serializer.deserialize(payload)) { "Empty data field" }
            streamLoggingService.doData(data)
            redisTemplate.opsForStream<String, ByteArray>().acknowledge(streamKey, group, record.id)
        }.onFailure { ex ->
            log.error("Failed to process Redis Stream record {} from {}", record.id, streamKey, ex)
        }
    }

    fun recoverPending() {
        val stream = redisTemplate.opsForStream<String, ByteArray>()
        var range: Range<String> = Range.unbounded()

        while (true) {
            val pending = stream.pending(streamKey, group, range, RECOVERY_BATCH_SIZE, MIN_IDLE_TIME)
            if (pending.isEmpty) return

            for (message in pending) {
                val claimed = stream.claim(streamKey, group, consumerName, MIN_IDLE_TIME, message.id)
                claimed.forEach(::processRecord)
            }

            if (pending.size() < RECOVERY_BATCH_SIZE) return
            range = Range.rightUnbounded(Range.Bound.exclusive(pending.get(pending.size() - 1).idAsString))
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(RedisStreams::class.java)
        private val MIN_IDLE_TIME = Duration.ofSeconds(60)
        private const val RECOVERY_INTERVAL_SECONDS = 15L
        private const val RECOVERY_BATCH_SIZE = 100L
    }
}
