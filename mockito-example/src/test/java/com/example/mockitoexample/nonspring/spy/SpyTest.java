package com.example.mockitoexample.nonspring.spy;

import com.example.mockitoexample.component.EntityIdCreator;
import com.example.mockitoexample.domain.Entity;
import com.example.mockitoexample.repository.TestRepository;
import com.example.mockitoexample.service.TestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SpyTest {
    @InjectMocks
    TestService testService;
    @Spy
    TestRepository testRepository;
    @Spy
    EntityIdCreator entityIdCreator;

    @Test
    void mockInjectionTest() throws Exception {
        // given
        Entity entity = Entity.builder()
                .id(1L)
                .name("name")
                .build();

        Mockito.doAnswer(invocation -> 1L)
                .when(entityIdCreator).generateNewId();
        Mockito.doAnswer(invocation -> Collections.emptyList())
                .when(testRepository).findAll();

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
