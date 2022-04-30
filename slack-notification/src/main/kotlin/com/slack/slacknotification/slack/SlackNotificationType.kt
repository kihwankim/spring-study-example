package com.slack.slacknotification.slack

enum class SlackNotificationType(
    private val template: String
) {
    ERROR_MESSAGE(
        """
        [에러 코드]
        %s
        
        [요청 URI]
        %s
        
        [발생 시간]
        %s
    """.trimIndent()
    ),
    INFO_MESSAGE(
        """
        [요청 URI]
        %s
        
        [발생 시간]
        %s
    """.trimIndent()
    );

    fun generateMessage(vararg args: Any?): String {
        return String.format(template, *args)
    }
}