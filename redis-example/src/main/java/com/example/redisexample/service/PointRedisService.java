package com.example.redisexample.service;

import com.example.redisexample.domain.Point;
import com.example.redisexample.dto.PointDto;
import com.example.redisexample.repository.PointRedisRepository;
import com.example.redisexample.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointRedisService {
    private final PointRedisRepository pointRedisRepository;
    private final PointRepository pointRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public PointDto savePoint(Long id) {
        Optional<PointDto> optional = pointRedisRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }

        PointDto savePoint = pointRedisRepository.save(PointDto.builder()
                .id(id)
                .amount(123L)
                .refreshTime(LocalDateTime.now())
                .build());
        restorePointByDBRollback(savePoint);

        pointRepository.save(PointDto.toPoint(savePoint));
        throw new RuntimeException();

//        return savePoint;
    }

    private void restorePointByDBRollback(PointDto point) {
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCompletion(int status) {
                        if (status == STATUS_ROLLED_BACK) {
                            pointRedisRepository.delete(point);
                        }
                    }
                });
    }

    @Transactional
    public List<PointDto> saveAllPoints(String userId) {
        final String key = userId + ":point";

        List<Object> pointListObject = redisTemplate.execute(
                new SessionCallback<>() {
                    @Override
                    public List<Object> execute(RedisOperations operations) throws DataAccessException {
                        try {
                            operations.watch(key);
                            operations.multi();
                            operations.opsForList().range(key, 0, -1);
                            operations.delete(key);
                            return operations.exec();
                        } catch (Exception e) {
                            operations.discard();
                            throw e;
                        }
                    }
                }
        );
        List<PointDto> pointDtos = (List<PointDto>) pointListObject.get(0);
        restorePointAll(userId, pointDtos); // restore function

        List<Point> points = pointDtos.stream()
                .map(pointDto -> Point.builder().amount(pointDto.getAmount()).build())
                .collect(Collectors.toList());

        pointRepository.saveAll(points);

        return pointDtos;
    }

    private void restorePointAll(String userId, List<PointDto> points) {
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCompletion(int status) {
                        if (status == STATUS_ROLLED_BACK) {
                            insertPointsList(userId, points);
                        }
                    }
                });
    }

    private void insertPointsList(String userId, List<PointDto> pointDtos) {
        final String key = userId + ":point";

        RedisSerializer keySerializer = redisTemplate.getStringSerializer();
        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();

        redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            pointDtos.forEach(point -> redisConnection.rPush(keySerializer.serialize(key), valueSerializer.serialize(point)));
            return null;
        });
    }

    public void hscanDataFromHash() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            ScanOptions options = ScanOptions.scanOptions().match("*").count(200).build();
            Cursor<Map.Entry<byte[], byte[]>> entries = connection.hScan("key_val".getBytes(), options);

            while (entries.hasNext()) {
                Map.Entry<byte[], byte[]> next = entries.next();
                byte[] actualKey = next.getKey();
                byte[] actualValue = next.getValue();
                String key = new String(actualKey);
                String val = new String(actualValue);
                log.info("hash key: {}, hash value: {}", key, val);
            }

            return null;
        });
    }

    public void saveHashMapValue() {
        Map<String, String> mapValue = Map.of(
                "firstName", "fName",
                "lastName", "lName"
        );
        redisTemplate.opsForHash()
                .putAll("key_val", mapValue);
    }

    public void getAllMapValue() {
        Map<Object, Object> keyValValue = redisTemplate.opsForHash().entries("key_val");
        Set<Object> objects = keyValValue.keySet();
        for (Object key : objects) {
            log.info("key: {}, value: {}", key, keyValValue.get(key));
        }
    }

    public void getMultiGet() {
        List<Object> objects = redisTemplate.opsForHash().multiGet("key_val", List.of("firstName"));
        for (Object eachValue : objects) {
            log.info("value: {}", eachValue);
        }
    }

    public void getForOnlyValue() {
        Object value = redisTemplate.opsForHash().get("key_val", "firstName");
        log.info("key: firstName, value: {}", value);
    }

    public void saveListToRight(String val) {
        final String key = "list_val";
        Long index = redisTemplate.opsForList().rightPush(key, val);
        redisTemplate.expire(key, 1000, TimeUnit.MICROSECONDS);

        log.info("index: {}", index);
    }

    public void findAllFromList() {
        List<String> listVals = redisTemplate.opsForList().range("list_val", 0, -1);
        if (Objects.isNull(listVals)) {
            listVals = List.of();
        }

        for (String eachVal : listVals) {
            log.info("each val: {}", eachVal);
        }
    }
}
