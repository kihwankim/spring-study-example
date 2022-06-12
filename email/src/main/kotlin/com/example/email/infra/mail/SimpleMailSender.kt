package com.example.email.infra.mail

import com.example.email.application.mail.MailSender
import org.springframework.boot.autoconfigure.mail.MailProperties
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Component
class SimpleMailSender(
    private val mailProperties: MailProperties,
    private val mailSender: JavaMailSender
) : MailSender {
    override fun send(toAddress: String, subject: String, body: String) {
        val message = mailSender.createMimeMessage()
        MimeMessageHelper(message).apply {
            setFrom(mailProperties.username)
            setTo(toAddress)
            setSubject(subject)
            setText(body, true)
        }
        mailSender.send(message)
    }
}