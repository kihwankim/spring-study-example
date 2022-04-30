package com.slack.slacknotification

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class SlackNotificationApplication

fun main(args: Array<String>) {
    runApplication<SlackNotificationApplication>(*args)
}
