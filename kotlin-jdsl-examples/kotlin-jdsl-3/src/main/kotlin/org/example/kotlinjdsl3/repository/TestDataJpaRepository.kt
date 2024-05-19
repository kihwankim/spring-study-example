package org.example.kotlinjdsl3.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.example.kotlinjdsl3.entity.TestDataJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TestDataJpaRepository : JpaRepository<TestDataJpaEntity, Long>, KotlinJdslJpqlExecutor