package com.example.mockitoexample.service;

import com.example.mockitoexample.component.EntityIdCreator;
import com.example.mockitoexample.domain.Entity;
import com.example.mockitoexample.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final EntityIdCreator entityIdCreator;

    public Long save(Entity entity) {
        entity.setId(entityIdCreator.generateNewId());
        return testRepository.save(entity).getId();
    }

    public List<Entity> findEntiy() {
        return testRepository.findAll();
    }

    public Entity findById(Long id) {
        return testRepository.findById(id);
    }
}
