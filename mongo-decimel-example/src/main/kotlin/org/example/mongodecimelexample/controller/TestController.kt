package org.example.mongodecimelexample.controller

import org.example.mongodecimelexample.mongo.TestDataMongo
import org.example.mongodecimelexample.mongo.TestDataMongoRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class TestController(
    private val testDataMongoRepository: TestDataMongoRepository,
) {

    @GetMapping("/save")
    fun saveData(@RequestParam price: BigDecimal) {
        testDataMongoRepository.save(
            TestDataMongo(
                price = price
            )
        )
    }

    @GetMapping("/gt-price")
    fun findGtPrice(@RequestParam price: BigDecimal) = testDataMongoRepository.findByGtPrice(price)
}