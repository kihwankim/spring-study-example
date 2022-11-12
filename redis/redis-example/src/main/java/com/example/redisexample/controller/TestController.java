package com.example.redisexample.controller;


import com.example.redisexample.dto.PointDto;
import com.example.redisexample.repository.PointRedisRepository;
import com.example.redisexample.service.PointRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final PointRedisService pointRedisService;
    private final PointRedisRepository pointRedisRepository;

    @GetMapping("/point")
    public PointDto visit(Long id) {
        return pointRedisService.savePoint(id);
    }

    @GetMapping("/list")
    public List<PointDto> getAll() {
        return (List<PointDto>) pointRedisRepository.findAll();
    }
}
