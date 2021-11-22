package com.example.redisexample;

import com.example.redisexample.domain.Point;
import com.example.redisexample.repository.PointRedisRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RedisExampleApplicationTests {
    @Autowired
    PointRedisRepository pointRedisRepository;

    @AfterEach
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @Test
    void addPointTest_Success() {
        //given
        String id = "idData";
        LocalDateTime refreshTime = LocalDateTime.of(2021, 11, 22, 0, 0);
        Point point = Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();

        //when
        pointRedisRepository.save(point);

        //then
        Point savedPoint = pointRedisRepository.findById(id).get();
        assertEquals(1000L, savedPoint.getAmount());
        assertEquals(refreshTime, savedPoint.getRefreshTime());
    }
}
