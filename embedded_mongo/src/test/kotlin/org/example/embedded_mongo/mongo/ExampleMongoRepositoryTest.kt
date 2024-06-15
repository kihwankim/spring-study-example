package org.example.embedded_mongo.mongo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataMongoTest
@ExtendWith(SpringExtension::class)
class ExampleMongoRepositoryTest {
    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Autowired
    lateinit var exampleMongoRepository: ExampleMongoRepository

    @AfterEach
    fun clean() {
        exampleMongoRepository.deleteAll()
    }

    @Test
    fun saveAndFindExample() {
        // given
        val mock = ExampleMongo(
            name = "owa"
        )
        mongoTemplate.save(mock)

        // when
        val data = exampleMongoRepository.findAll()

        // then
        assertThat(data).isNotNull
        assertThat(data).hasSize(1)
    }

    @Test
    fun saveAndFindExample2() {
        // given
        val mock = ExampleMongo(
            name = "owa1"
        )
        mongoTemplate.save(mock)

        // when
        val data = exampleMongoRepository.findAll()

        // then
        assertThat(data).isNotNull
        assertThat(data).hasSize(1)
    }
}