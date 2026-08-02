package com.example.parallel_consumer.domain.common.log.supporter

import com.example.parallel_consumer.domain.common.log.constants.SPAN_ID
import com.example.parallel_consumer.domain.common.log.constants.TRACE_ID
import org.slf4j.MDC

object LoggingHelper {
    fun createNextTraceHeader(): String? =
        fetchTraceId()?.let { traceId ->
            fetchSpanId()?.let { spanId ->
                "00-$traceId-$spanId-00"
            }
        }

    private fun fetchTraceId(): String? = MDC.get(TRACE_ID)

    private fun fetchSpanId(): String? = MDC.get(SPAN_ID)
}
