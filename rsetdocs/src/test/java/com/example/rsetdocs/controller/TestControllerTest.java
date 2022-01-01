package com.example.rsetdocs.controller;

import com.example.rsetdocs.api.ApiDocs;
import com.example.rsetdocs.dto.HelloResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestController.class)
class TestControllerTest extends ApiDocs {

    @Test
    void getMappginTest_Success() throws Exception {
        // given

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/hello")
                .queryParam("message", "message data 메시지")
                .queryParam("id", String.valueOf(1L))
                .header(HttpHeaders.AUTHORIZATION, "token"));

        // then

        HelloResponse helloResponse = objectMapper.readValue(response.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("hello-get"))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
        });

        assertEquals("reply: message data 메시지", helloResponse.getReplyMessage());
        assertEquals(1L, helloResponse.getId());
    }
}