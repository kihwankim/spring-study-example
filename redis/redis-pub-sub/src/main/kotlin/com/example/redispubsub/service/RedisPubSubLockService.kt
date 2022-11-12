package com.example.redispubsub.service

import com.example.redispubsub.pub.LockPublisher
import com.example.redispubsub.repository.RedisRepository
import org.springframework.data.redis.listener.ChannelTopic

class RedisPubSubLockService(
    private val lockPublisher: LockPublisher,
    private val redisRepository: RedisRepository
) {

    fun getLock() {
        val topic = ChannelTopic.of("lockTopic")
        val lockName = "lock"
        val lockVal = "true"
        val lockExpireTime = 3L

        if (!redisRepository.setNx(lockName, lockExpireTime, lockVal)) {
            handleFailOfGettingLock()
        }

        bizLogic()

        redisRepository.delByKey(lockName)
        lockPublisher.publishLock(topic, lockName)
    }

    private fun bizLogic() {
        Thread.sleep(5L)
        println("logic")
        Thread.sleep(5L)
    }

    private fun handleFailOfGettingLock() {
        println("handle")
    }
}