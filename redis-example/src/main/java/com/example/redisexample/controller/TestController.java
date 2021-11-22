package com.example.redisexample.controller;


import com.example.redisexample.domain.Point;
import com.example.redisexample.repository.PointRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final PointRedisRepository pointRedisRepository;

    @GetMapping("/point")
    public Point visit(String id) {
        Optional<Point> optional = pointRedisRepository.findById(id);
        if (optional.isPresent()) {
            Point point = optional.get();
            return pointRedisRepository.save(point);
        }

        return pointRedisRepository.save(Point.builder()
                .id(id)
                .amount(123L)
                .refreshTime(LocalDateTime.now())
                .build());
    }

    @GetMapping("/list")
    public List<Point> getAll() {
        return (List<Point>) pointRedisRepository.findAll();
    }
}
