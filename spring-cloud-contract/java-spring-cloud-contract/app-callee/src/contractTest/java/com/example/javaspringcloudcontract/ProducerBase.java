package com.example.javaspringcloudcontract;

import com.example.javaspringcloudcontract.presentation.CalleController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

@AutoConfigureMessageVerifier
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProducerBase {
    @Autowired
    CalleController calleController;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(calleController);
    }
}