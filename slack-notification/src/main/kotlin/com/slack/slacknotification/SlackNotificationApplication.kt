package com.slack.slacknotification

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SlackNotificationApplication

fun main(args: Array<String>) {
    runApplication<SlackNotificationApplication>(*args)
}
