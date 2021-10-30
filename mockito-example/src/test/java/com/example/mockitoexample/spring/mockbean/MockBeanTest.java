package com.example.mockitoexample.spring.mockbean;

import com.example.mockitoexample.component.EntityIdCreator;
import com.example.mockitoexample.domain.Entity;
import com.example.mockitoexample.repository.TestRepository;
import com.example.mockitoexample.service.TestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;

@SpringBootTest
public class MockBeanTest {
    @Autowired
    TestService testService;
    @MockBean
    TestRepository testRepository;
    @MockBean
    EntityIdCreator entityIdCreator;

    @Test
    void mockInjectionTest() throws Exception {
        // given
        Entity entity = Entity.builder()
                .id(1L)
                .name("name")
                .build();

        willReturn(1L)
                .given(entityIdCreator).generateNewId();
        willReturn(entity)
                .given(testRepository).save(entity);

        // when
        Long saveId = testService.save(entity);

        // then
        assertEquals(1L, saveId);
        Mockito.verify(testRepository, Mockito.times(1)).save(entity);
        Mockito.verify(entityIdCreator, Mockito.times(1)).generateNewId();
    }
}
