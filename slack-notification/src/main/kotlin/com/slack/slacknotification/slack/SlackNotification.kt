package com.slack.slacknotification.slack

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping


@FeignClient(
    name = "slackNotification",
    url = "\${slack.base-url}",
    configuration = [
        SlackFeignConfig::class
    ],
    primary = false
)
interface SlackNotification {

    @PostMapping("\${slack.token}")
    fun postMonitoringMessage(request: PostNotificationMessageRequest)
}