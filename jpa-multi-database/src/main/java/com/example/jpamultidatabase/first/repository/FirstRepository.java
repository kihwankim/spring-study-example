package com.example.jpamultidatabase.first.repository;

import com.example.jpamultidatabase.first.entity.FirstEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstRepository extends JpaRepository<FirstEntity, Long> {
}
