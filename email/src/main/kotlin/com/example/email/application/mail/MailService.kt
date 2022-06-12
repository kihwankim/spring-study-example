package com.example.email.application.mail

import com.example.email.presentataion.dto.EmailSendDto
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.ISpringTemplateEngine

@Service
class MailService(
    private val mailSender: MailSender,
    private val templateEngine: ISpringTemplateEngine,
) {

    fun sendFormSubmittedMail(emailSend: EmailSendDto) {
        val context = Context().apply {
            setVariables(
                mapOf(
                    "header" to emailSend.header,
                    "value" to emailSend.value,
                )
            )
        }
        mailSender.send(
            emailSend.email,
            "subject: ${emailSend.subject}",
            templateEngine.process("mail/template-val", context)
        )
    }
}