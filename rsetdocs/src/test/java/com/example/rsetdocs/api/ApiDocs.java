package com.example.rsetdocs.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
public abstract class ApiDocs {
    @Autowired
    protected MockMvc mockMvc;

    protected static ObjectMapper objectMapper = new ObjectMapper();

    protected static OperationRequestPreprocessor getDocumentRequest() { // save request data creation
        return preprocessRequest(
                modifyUris()
                        .scheme("http")
                        .host("localhost")
                        .removePort(),
                prettyPrint());
    }

    protected static OperationResponsePreprocessor getDocumentResponse() { // save response creation
        return preprocessResponse(prettyPrint());
    }

    protected static RestDocumentationResultHandler toDocument(String title) { // save file
        return document(title, getDocumentRequest(), getDocumentResponse());
    }

    protected static String toJson(Object object) { // write for request body
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new IllegalStateException("직렬화 오류");
        }
    }
}
