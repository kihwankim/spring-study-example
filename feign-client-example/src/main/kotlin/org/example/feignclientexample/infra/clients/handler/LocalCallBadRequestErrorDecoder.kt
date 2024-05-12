package org.example.feignclientexample.infra.clients.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import feign.Response
import feign.codec.ErrorDecoder
import mu.KotlinLogging
import org.example.feignclientexample.commons.enums.ErrorType
import org.example.feignclientexample.commons.exceptions.AppException
import org.example.feignclientexample.commons.exceptions.ExternalIgnoreException
import org.example.feignclientexample.web.dto.ErrorResponse
import java.io.IOException

class LocalCallBadRequestErrorDecoder(
    private val objectMapper: ObjectMapper,
) : ErrorDecoder {
    companion object {
        private val log = KotlinLogging.logger { }
    }

    override fun decode(str: String?, response: Response?): Exception {
        return try {
            response?.body()?.asInputStream()?.use { responseBody ->
                val body = objectMapper.readValue<ErrorResponse>(responseBody)
                if (body.errorCode == ErrorType.BAD_REQUEST.name) {
                    ExternalIgnoreException(errorType = ErrorType.BAD_IGNORE_ERROR)
                } else {
                    AppException(
                        errorCode = body.errorCode,
                        errorMessage = body.message,
                        statusCode = response.status(),
                    )
                }
            } ?: AppException(errorType = ErrorType.BAD_GATEWAY)
        } catch (e: IOException) {
            log.warn("body read exception", e)
            AppException(errorType = ErrorType.BAD_GATEWAY)
        }
    }

}