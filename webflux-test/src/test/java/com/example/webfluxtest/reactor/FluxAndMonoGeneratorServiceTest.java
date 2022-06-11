package com.example.webfluxtest.reactor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService;

    @BeforeEach
    void init() {
        this.fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
    }

    @Test
    void namesFluxTest() {
        // given

        // when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFlux();

        // then
        StepVerifier.create(namesFlux)
                .expectNextCount(3)
                .verifyComplete();

        StepVerifier.create(namesFlux)
                .expectNext("alex", "ben", "chloe")
                .verifyComplete();
    }

    @Test
    void namesFlusMapTest() throws Exception {
        // given

        // when
        Flux<String> namesFluxMap = fluxAndMonoGeneratorService.namesFluxMap();

        // then
        StepVerifier.create(namesFluxMap)
                .expectNext("ALEX", "BEN", "CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFlusFlatMapTest() throws Exception {
        // given

        // when
        Flux<String> namesFluxMap = fluxAndMonoGeneratorService.namesFluxFlabMap();

        // then
        StepVerifier.create(namesFluxMap)
                .expectNext("A", "L", "E", "X", "B", "E", "N", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void nameMonoTest() {
    }
}