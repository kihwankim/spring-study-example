package com.example.email.presentataion.controller

import com.example.email.application.mail.MailService
import com.example.email.presentataion.dto.EmailSendDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MailController(
    private val mailService: MailService
) {
    @PostMapping("/mail")
    fun sendMail(@RequestBody emailSendDto: EmailSendDto): ResponseEntity<Nothing> {
        mailService.sendFormSubmittedMail(emailSendDto)

        return ResponseEntity.ok().build()
    }
}