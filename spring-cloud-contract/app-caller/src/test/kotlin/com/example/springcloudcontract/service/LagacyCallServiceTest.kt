package com.example.springcloudcontract.service

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.anyUrl
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.web.client.RestTemplate

@SpringBootTest
@AutoConfigureWireMock
class LagacyCallServiceTest {

    lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun setUp() {
        restTemplate = RestTemplate()
    }

    @Test
    @Throws(Exception::class)
    fun `과거 방식 web client test`() {
        // given
        val responseJson = "even val"

        WireMock.stubFor(
            WireMock.get(anyUrl())
                .withQueryParams(
                    mapOf(
                        "num" to equalTo("2")
                    )
                )
                .withQueryParam("num", equalTo("2"))
                .willReturn(WireMock.aResponse().withStatus(200).withBody(responseJson))
        )

        // when
//        val httpHeaders = HttpHeaders()
//        httpHeaders.contentType = MediaType.APPLICATION_JSON
//        val httpEntity = HttpEntity<String>(httpHeaders)
        val responseEntity = restTemplate.getForEntity("http://localhost:8080/api/v1/caller?num=2", String::class.java)

        // then
        assertEquals(200, responseEntity.statusCodeValue)
        assertEquals("even val", responseEntity.body)
    }
}