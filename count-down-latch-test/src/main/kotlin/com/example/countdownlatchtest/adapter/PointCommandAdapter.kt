package com.example.countdownlatchtest.adapter

import com.example.countdownlatchtest.repository.PointJpaEntity
import com.example.countdownlatchtest.repository.PointJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class PointCommandAdapter(
    private val pointJpaRepository: PointJpaRepository
) {

    fun createPoint(point: Long, userId: Long) {
        PointJpaEntity(
            remainPoint = point,
            userId = userId,
            recvPoint = point,
            pointType = PointJpaEntity.PointType.ADD,
            pointStatus = PointJpaEntity.PointStatus.CREATE,
        )
    }

    fun usePoint(userId: Long, pointForUse: Long) {
        val foundPoint = pointJpaRepository.findByUserIdAndPointStatus(userId, PointJpaEntity.PointStatus.CREATE)

        if (pointForUse > foundPoint.sumOf { it.remainPoint }) {
            throw IllegalArgumentException()
        }

        var sumOfPoint = 0L
        for (unusedPoint in foundPoint) {
            if (sumOfPoint == pointForUse) {
                break
            }

            if (sumOfPoint < pointForUse) {
                val gap = pointForUse - sumOfPoint
                if (gap < unusedPoint.remainPoint) {
                    sumOfPoint += gap
                    unusedPoint.usePoint(gap)
                } else {
                    sumOfPoint += unusedPoint.remainPoint
                    unusedPoint.usePoint(unusedPoint.remainPoint)
                }
            }
        }
    }
}