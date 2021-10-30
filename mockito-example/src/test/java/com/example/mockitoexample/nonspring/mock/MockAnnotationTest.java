package com.example.mockitoexample.nonspring.mock;

import com.example.mockitoexample.component.EntityIdCreator;
import com.example.mockitoexample.domain.Entity;
import com.example.mockitoexample.repository.TestRepository;
import com.example.mockitoexample.service.TestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MockAnnotationTest {
    @InjectMocks
    TestService testService;
    @Mock
    TestRepository testRepository;
    @Mock
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
        Mockito.doAnswer(invocation -> entity)
                .when(testRepository).save(entity);

        // when
        Long saveId = testService.save(entity);

        // then
        assertEquals(1L, saveId);
        Mockito.verify(testRepository, Mockito.times(1)).save(entity);
        Mockito.verify(entityIdCreator, Mockito.times(1)).generateNewId();
    }
}
