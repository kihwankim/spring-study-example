package com.slack.slacknotification

import com.slack.slacknotification.slack.PostNotificationMessageRequest
import com.slack.slacknotification.slack.SlackNotification
import com.slack.slacknotification.slack.SlackNotificationType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class SlackNotificationApplicationTests {
    @Autowired
    lateinit var notification: SlackNotification

    @Test
    fun sendMessageTest() {
        val message = PostNotificationMessageRequest(
            SlackNotificationType.INFO_MESSAGE.generateMessage(
                "test url",
                LocalDateTime.now()
            )
        )

        notification.postMonitoringMessage(message)
    }
}
