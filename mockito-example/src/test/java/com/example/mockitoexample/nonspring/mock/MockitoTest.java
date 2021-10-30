package com.example.mockitoexample.nonspring.mock;

import com.example.mockitoexample.component.EntityIdCreator;
import com.example.mockitoexample.domain.Entity;
import com.example.mockitoexample.repository.TestRepository;
import com.example.mockitoexample.service.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MockitoTest {

    TestService testService;
    TestRepository testRepository;
    EntityIdCreator entityIdCreator;

    @BeforeEach
    void setUp() {
        testRepository = Mockito.mock(TestRepository.class);
        entityIdCreator = Mockito.mock(EntityIdCreator.class);

        testService = new TestService(testRepository, entityIdCreator);
    }

    @Test
    void createWithMockTest() throws Exception {
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