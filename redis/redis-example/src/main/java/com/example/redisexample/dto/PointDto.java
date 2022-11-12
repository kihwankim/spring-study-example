package com.example.redisexample.dto;


import com.example.redisexample.domain.Point;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@RedisHash("point")
public class PointDto implements Serializable {

    @Id
    private Long id;
    private Long amount;
    private LocalDateTime refreshTime;

    @Builder
    public PointDto(Long id, Long amount, LocalDateTime refreshTime) {
        this.id = id;
        this.amount = amount;
        this.refreshTime = refreshTime;
    }

    public void refresh(long amount, LocalDateTime refreshTime) {
        if (refreshTime.isAfter(this.refreshTime)) { // 저장된 데이터보다 최신 데이터일 경우
            this.amount = amount;
            this.refreshTime = refreshTime;
        }
    }

    public static Point toPoint(PointDto pointDto) {
        return Point.builder()
                .amount(pointDto.getAmount())
                .build();
    }
}