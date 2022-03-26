package com.example.redisexample.repository;

import com.example.redisexample.dto.PointDto;
import org.springframework.data.repository.CrudRepository;

public interface PointRedisRepository extends CrudRepository<PointDto, Long> {
}
