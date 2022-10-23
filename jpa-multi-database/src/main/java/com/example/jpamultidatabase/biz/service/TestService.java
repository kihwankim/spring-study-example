package com.example.jpamultidatabase.biz.service;

import com.example.jpamultidatabase.first.entity.FirstEntity;
import com.example.jpamultidatabase.first.repository.FirstRepository;
import com.example.jpamultidatabase.second.entity.SecondEntity;
import com.example.jpamultidatabase.second.repository.SecondRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {
    private final FirstRepository firstRepository;
    private final SecondRepository secondRepository;

    @Transactional(readOnly = true)
    public void readAll() {
        List<FirstEntity> first = firstRepository.findAll();
        List<SecondEntity> second = secondRepository.findAll();

        for (FirstEntity FirstEntity : first) {
            log.info("first name: {}", FirstEntity.getName());
        }

        for (SecondEntity secondEntity : second) {
            log.info("first name: {}", secondEntity.getName());
        }
    }

    @Transactional
    public void saveEachOne() {
        FirstEntity firstEntity = new FirstEntity(getTmpName(), getTmpName());

        SecondEntity secondEntity = new SecondEntity(getTmpName(), getTmpName());

        FirstEntity savedFirst = firstRepository.save(firstEntity);
        SecondEntity savedSecond = secondRepository.save(secondEntity);

        log.info("saved first Id: {}", savedFirst.getId());
        log.info("saved second Id: {}", savedSecond.getId());
    }

    @Transactional
    public void updateNames() {
        List<FirstEntity> first = firstRepository.findAll();
        List<SecondEntity> second = secondRepository.findAll();

        if (first.size() > 0) {
            FirstEntity firstEntity = first.get(0);
            String before = firstEntity.getName();
            firstEntity.setName(getTmpName());

            log.info("before: {}, after: {}", before, firstEntity.getName());
        }

        if (second.size() > 0) {
            SecondEntity secondEntity = second.get(0);
            String before = secondEntity.getName();
            secondEntity.setName(getTmpName());

            log.info("before: {}, after: {}", before, secondEntity.getName());
        }
    }

    private String getTmpName() {
        return UUID.randomUUID().toString().substring(0, 20);
    }
}
