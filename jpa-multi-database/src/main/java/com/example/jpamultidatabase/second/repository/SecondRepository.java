package com.example.jpamultidatabase.second.repository;

import com.example.jpamultidatabase.second.entity.SecondEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondRepository extends JpaRepository<SecondEntity, Long> {
}
