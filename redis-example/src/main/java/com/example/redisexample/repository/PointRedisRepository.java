package com.example.redisexample.repository;

import com.example.redisexample.domain.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointRedisRepository extends CrudRepository<Point, String> {
}
