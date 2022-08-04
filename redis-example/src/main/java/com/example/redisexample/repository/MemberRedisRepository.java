package com.example.redisexample.repository;

import com.example.redisexample.dto.MemberDto;
import org.springframework.data.repository.CrudRepository;

public interface MemberRedisRepository extends CrudRepository<MemberDto, Long> {
}
