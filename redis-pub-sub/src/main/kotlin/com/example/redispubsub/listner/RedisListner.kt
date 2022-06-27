package com.example.redispubsub.listner

import io.lettuce.core.pubsub.RedisPubSubListener

class RedisListner : RedisPubSubListener<String, String> {
    override fun message(channel: String?, message: String?) {
        TODO("Not yet implemented")
    }

    override fun message(pattern: String?, channel: String?, message: String?) {
        TODO("Not yet implemented")
    }

    override fun subscribed(channel: String?, count: Long) {
        TODO("Not yet implemented")
    }

    override fun psubscribed(pattern: String?, count: Long) {
        TODO("Not yet implemented")
    }

    override fun unsubscribed(channel: String?, count: Long) {
        TODO("Not yet implemented")
    }

    override fun punsubscribed(pattern: String?, count: Long) {
        TODO("Not yet implemented")
    }
}