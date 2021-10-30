package com.example.mockitoexample.repository;

import com.example.mockitoexample.domain.Entity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TestRepository {
    private static final Map<Long, Entity> db = new HashMap<>();

    public Entity save(Entity saveCandidate) {
        db.put(saveCandidate.getId(), saveCandidate);
        return saveCandidate;
    }

    public Entity findById(Long id) {
        return db.get(id);
    }
}
