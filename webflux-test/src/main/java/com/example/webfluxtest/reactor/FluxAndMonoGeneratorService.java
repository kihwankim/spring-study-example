package com.example.webfluxtest.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class FluxAndMonoGeneratorService {
    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .log(); // DB or remote service call
    }

    public Flux<String> namesFluxMap() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .log(); // DB or remote service call
    }

    public Flux<String> namesFluxFlabMap() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .flatMap(this::splitString)
                .log(); // DB or remote service call
    }

    private Flux<String> splitString(String name) {
        char[] digits = name.toCharArray();
        String[] digitString = new String[digits.length];
        for (int i = 0; i < digits.length; i++) {
            digitString[i] = Character.toString(digits[i]);
        }

        return Flux.fromArray(digitString).delayElements(Duration.ofMillis(3000));
    }

    public Mono<String> nameMono() {
        return Mono.just("alex")
                .log();
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> System.out.println("Name is: " + name));

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(name -> System.out.println("Name is: " + name));
    }
}
