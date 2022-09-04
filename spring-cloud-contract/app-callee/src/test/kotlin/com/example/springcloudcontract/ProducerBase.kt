package com.example.springcloudcontract

import com.example.springcloudcontract.presentation.TestApiController
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
open class ProducerBase {
    @Autowired
    lateinit var testApiController: TestApiController

    @BeforeEach
    fun setup() {
        RestAssuredMockMvc.standaloneSetup(testApiController)
    }
}