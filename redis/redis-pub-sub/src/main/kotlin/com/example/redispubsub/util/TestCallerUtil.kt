package com.example.redispubsub.util

import com.example.redispubsub.Message
import mu.KotlinLogging
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.client.ClientHttpResponse
import org.springframework.util.StreamUtils
import org.springframework.web.client.ResponseErrorHandler
import java.io.IOException
import java.nio.charset.StandardCharsets

private val logger = KotlinLogging.logger {}

class TestCallerUtil {
    companion object {
        private val restTemplate = RestTemplateBuilder().errorHandler(ErorrHandler()).build()

        fun get(uri: String): String {
            return restTemplate.getForObject(uri, String::class.java)!!
        }

        fun <T> post(uri: String, body: T): String {
            return restTemplate.postForObject(uri, body, String::class.java)!!
        }

        fun call(threadCount: Int, uri: String) {
            for (i in 0 until threadCount) {
                Thread {
                    try {
                        val obj = get(uri)
                        logger.info(obj)
                    } catch (e: Exception) {
                        throw RuntimeException(e)
                    }
                }.start()
            }
        }

        fun callPost(threadCount: Int, uri: String) {
            for (i in 0 until threadCount) {
                Thread {
                    try {
                        val body = Message(
                            roomId = i.toLong(),
                            chat = i.toString()
                        )
                        val obj = post(uri, body)
                        logger.info(obj)
                    } catch (e: Exception) {
                        throw RuntimeException(e)
                    }
                }.start()
            }
        }

    }

    private class ErorrHandler : ResponseErrorHandler {
        @Throws(IOException::class)
        override fun hasError(response: ClientHttpResponse): Boolean {
            return response.statusCode.isError
        }

        @Throws(IOException::class)
        override fun handleError(response: ClientHttpResponse) {
            val message = StreamUtils.copyToString(response.body, StandardCharsets.UTF_8)
            logger.error(message)
        }
    }
}