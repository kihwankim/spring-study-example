package com.example.mockitoexample.repository;

import com.example.mockitoexample.domain.Entity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TestRepository {
    private static final Map<Long, Entity> db = new HashMap<>();

    public Entity save(Entity saveCandidate) {
        db.put(saveCandidate.getId(), saveCandidate);
        return saveCandidate;
    }

    public List<Entity> findAll() {
        System.out.println("spy test");

        Set<Map.Entry<Long, Entity>> entries = db.entrySet();

        return entries.stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public Entity findById(Long id) {
        return db.get(id);
    }
}
