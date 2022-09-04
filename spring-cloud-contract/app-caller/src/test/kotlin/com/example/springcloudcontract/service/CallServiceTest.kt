package com.example.springcloudcontract.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties

@SpringBootTest
@AutoConfigureStubRunner(
    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = ["com.example:app-callee:+:stubs:10010"],
)
internal class CallServiceTest {

    @Autowired
    lateinit var callService: CallService

    @Test
    @Throws(Exception::class)
    fun `call odd num test`() {
        val callData = callService.callData(2)

        assertThat(callData.code).isEqualTo(200)
        assertThat(callData.result).isEqualTo("even val")
    }

    @Test
    @Throws(Exception::class)
    fun `call even num test`() {
        val callData = callService.callData(1)

        assertThat(callData.code).isEqualTo(200)
        assertThat(callData.result).isEqualTo("odd val")
    }
}