package com.slack.slacknotification.slack

import feign.FeignException
import feign.Response
import feign.RetryableException
import feign.Retryer
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SlackFeignConfig {
    @Bean
    fun errorDecoder(): ErrorDecoder {
        return SlackApiErrorDecoder()
    }

    @Bean
    fun retryer(): Retryer {
        return Retryer.Default(1000, 2000, 3)
    }

    private class SlackApiErrorDecoder : ErrorDecoder {
        override fun decode(methodKey: String?, response: Response): Exception {
            val exception = FeignException.errorStatus(methodKey, response)
            return when (response.status()) {
                400, 403, 404, 410 -> IllegalStateException(
                    java.lang.String.format(
                        "Slack API 호출 중 클라이언트 에러가 발생하였습니다. status: (%s) message: (%s)",
                        response.status(),
                        response.body()
                    )
                )
                else -> RetryableException(
                    response.status(),
                    exception.message,
                    response.request().httpMethod(),
                    exception,
                    null,
                    response.request()
                )
            }
        }
    }
}