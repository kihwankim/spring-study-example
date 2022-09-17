package com.example.javaspringcloudcontract.service;

import com.example.javaspringcloudcontract.presentation.ResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWireMock
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = {"com.example:app-callee:+:stubs:10010"}
)
class CallServiceTest {
    @Autowired
    CallService callService;

    @Test
    void callOddNumTest() {
        ResponseBody callData = callService.callData(2);

        assertThat(callData.getCode()).isEqualTo(200);
        assertThat(callData.getResult()).isEqualTo("even val java");
    }

    @Test
    void callEvenNumTest() {
        ResponseBody callData = callService.callData(1);

        assertThat(callData.getCode()).isEqualTo(200);
        assertThat(callData.getResult()).isEqualTo("odd val java");
    }
}