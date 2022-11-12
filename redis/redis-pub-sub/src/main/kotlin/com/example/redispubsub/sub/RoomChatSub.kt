package com.example.redispubsub.sub

import com.example.redispubsub.dto.RoomChat
import com.example.redispubsub.util.fromByteArray
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class RoomChatSub : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        val roomChat = fromByteArray(message.body, RoomChat::class.java) // read chatting data
        println("data= $roomChat")
    }
}