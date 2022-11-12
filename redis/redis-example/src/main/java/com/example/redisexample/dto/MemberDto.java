package com.example.redisexample.dto;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@RedisHash("point")
public class MemberDto implements Serializable {
    @Id
    private Long id;
    private String name;
}
