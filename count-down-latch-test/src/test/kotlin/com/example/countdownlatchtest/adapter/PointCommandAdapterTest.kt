package com.example.countdownlatchtest.adapter

import com.example.countdownlatchtest.repository.PointJpaEntity
import com.example.countdownlatchtest.repository.PointJpaRepository
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.transaction.support.TransactionTemplate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

private val logger = KotlinLogging.logger { }

@SpringBootTest
internal class PointCommandAdapterTest {

    @Autowired
    lateinit var transactionTemplate: TransactionTemplate

    @Autowired
    lateinit var pointJpaRepository: PointJpaRepository

    @Test
    fun usePoint() {
        // given
        val threadService = Executors.newFixedThreadPool(2)
        val savedPoint = transactionTemplate.execute {
            pointJpaRepository.save(
                PointJpaEntity(
                    remainPoint = 10L,
                    recvPoint = 10L,
                    pointStatus = PointJpaEntity.PointStatus.CREATE,
                    userId = 1L,
                )
            )
        }!!

        val firstLatch = CountDownLatch(1)
        val latch = CountDownLatch(1)

        // when
        threadService.submit {
            try {
                transactionTemplate.execute {
                    val foundPoint = pointJpaRepository.findById(savedPoint.id).orElseThrow()
                    logger.info("1wait")
                    firstLatch.await()
                    logger.info("1release")
                    foundPoint.usePoint(5)
                }
                latch.countDown()
                logger.info("update")
            } catch (e: Exception) {
                logger.info("test1 error")
            }
        }

        // then
        assertThrows<ObjectOptimisticLockingFailureException> {
            transactionTemplate.execute {
                val foundPoint = pointJpaRepository.findById(savedPoint.id).orElseThrow()
                firstLatch.countDown()
                logger.info("2wait")
                latch.await()
                logger.info("2release")
                foundPoint.usePoint(5)
            }
        }
    }
}