package org.example.coroutinetxexample.persistence.jpa.entity

import org.springframework.data.jpa.repository.JpaRepository

interface TestDataJpaRepository : JpaRepository<TestDataJpaEntity, Long>