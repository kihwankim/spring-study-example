package com.example.countdownlatchtest.repository

import javax.persistence.*

@Entity
@Table(name = "point")
class PointJpaEntity(
    @Id
    @Column(name = "point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var remainPoint: Long,
    var recvPoint: Long,
    @Enumerated(EnumType.STRING)
    var pointType: PointType,
    @Enumerated(EnumType.STRING)
    var pointStatus: PointStatus,
    var userId: Long,
    @Version
    var version: Int = 0,
) {

    enum class PointType {
        ADD, USE
    }

    enum class PointStatus {
        USED, CREATE
    }

    fun usePoint(usedPoint: Long) {
        if (remainPoint < usedPoint) {
            throw IllegalArgumentException()
        } else if (remainPoint == usedPoint) {
            remainPoint = 0L
            pointStatus = PointStatus.USED
        } else {
            remainPoint -= usedPoint
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PointJpaEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}