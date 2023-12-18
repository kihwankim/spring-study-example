package com.example.r2dbmysqlexample.persistence.respository

import com.example.r2dbmysqlexample.persistence.entity.TestTlb
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface TestTlbRepository : ReactiveCrudRepository<TestTlb, Long>