package com.example.redisexample;

import com.example.redisexample.dto.PointDto;
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
        Long id = 1L;
        LocalDateTime refreshTime = LocalDateTime.of(2021, 11, 22, 0, 0);
        PointDto pointDto = PointDto.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();

        //when
        pointRedisRepository.save(pointDto);

        //then
        PointDto savedPointDto = pointRedisRepository.findById(id).get();
        assertEquals(1000L, savedPointDto.getAmount());
        assertEquals(refreshTime, savedPointDto.getRefreshTime());
    }
}
