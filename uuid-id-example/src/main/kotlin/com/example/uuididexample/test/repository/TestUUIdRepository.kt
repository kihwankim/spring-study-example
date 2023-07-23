package com.example.uuididexample.test.repository

import com.example.uuididexample.test.entity.TestUUIdEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TestUUIdRepository : JpaRepository<TestUUIdEntity, String>