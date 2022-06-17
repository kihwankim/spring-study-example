package com.example.webfluxtest.reactor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

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
    void nameMonoFlatMapTest() {
        // given
        int stringLen = 3;

        // when
        Mono<List<String>> monoList = fluxAndMonoGeneratorService.namesMonoFlatMap(stringLen);

        // then
        StepVerifier.create(monoList)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }

    @Test
    void namesFluxTransformTest() throws Exception {
        // given
        int stringLen = 3;

        // when
        Flux<String> namesFluxTransaformResult = fluxAndMonoGeneratorService.namesFluxTransform(stringLen);

        // then
        StepVerifier.create(namesFluxTransaformResult)
                .expectNext("A", "L", "E", "X", "B", "E", "N", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void namesFluxTransform_2_Test() throws Exception {
        // given
        int stringLen = 6;

        // when
        Flux<String> namesFluxTransaformResult = fluxAndMonoGeneratorService.namesFluxTransform(stringLen);

        // then
        StepVerifier.create(namesFluxTransaformResult)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFluxTransform_Empty_Test() throws Exception {
        // given
        int stringLen = 6;

        // when
        Flux<String> namesFluxTransaformResult = fluxAndMonoGeneratorService.namesFluxTransformEmpty(stringLen);

        // then
        StepVerifier.create(namesFluxTransaformResult)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }
}