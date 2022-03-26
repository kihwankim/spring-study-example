package com.example.redisexample.service;

import com.example.redisexample.dto.PointDto;
import com.example.redisexample.repository.PointRedisRepository;
import com.example.redisexample.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointRedisService {
    private final PointRedisRepository pointRedisRepository;
    private final PointRepository pointRepository;

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
//        throw new RuntimeException();

        return savePoint;
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
}
