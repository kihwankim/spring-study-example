package com.example.mockitoexample.spring.spybean;

import com.example.mockitoexample.component.EntityIdCreator;
import com.example.mockitoexample.domain.Entity;
import com.example.mockitoexample.repository.TestRepository;
import com.example.mockitoexample.service.TestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;

@SpringBootTest
public class SpyBeanTest {
    @Autowired
    TestService testService;
    @SpyBean
    TestRepository testRepository;
    @SpyBean
    EntityIdCreator entityIdCreator;

    @Test
    void spyBeanTest() throws Exception {
        // given
        Entity entity = Entity.builder()
                .id(1L)
                .name("name")
                .build();

        willReturn(1L)
                .given(entityIdCreator).generateNewId();
        willReturn(Collections.emptyList())
                .given(testRepository).findAll();

        // when
        Long saveId = testService.save(entity);
        Entity findEntity = testService.findById(1L);
        List<Entity> list = testService.findEntiy();


        // then
        assertEquals(0, list.size());
        assertEquals(1L, saveId);
        assertEquals(1L, findEntity.getId());
        Mockito.verify(testRepository, Mockito.times(1)).findAll();
        Mockito.verify(entityIdCreator, Mockito.times(1)).generateNewId();
    }
}
