package com.example.redispubsub.controller

import com.example.redispubsub.dto.RoomChat
import com.example.redispubsub.pub.RoomChatPub
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PubSubController(
    private val roomChatPub: RoomChatPub
) {

    @PostMapping("/message")
    fun pubMessage(@RequestBody roomChat: RoomChat): ResponseEntity<String> {
        roomChatPub.publish(roomChat)

        return ResponseEntity.ok(roomChat.chat)
    }
}