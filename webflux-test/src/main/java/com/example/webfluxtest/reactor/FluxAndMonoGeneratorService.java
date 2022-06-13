package com.example.webfluxtest.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

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

        return Flux.fromArray(digitString);
    }

    private Flux<String> splitStringWithDelay(String name) {
        char[] digits = name.toCharArray();
        String[] digitString = new String[digits.length];
        for (int i = 0; i < digits.length; i++) {
            digitString[i] = Character.toString(digits[i]);
        }

        return Flux.fromArray(digitString)
                .delayElements(Duration.ofMillis(3000));
    }

    public Flux<String> namesFluxFlatMapMany(int stringLen) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLen)
                .flatMapMany(this::splitString)
                .log();
    }

    public Flux<String> namesFluxTransform(int stringLen) {
        Function<Flux<String>, Flux<String>> fliterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() >= stringLen);
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(fliterMap)
                .flatMap(this::splitString)
                .log();
    }

    public Mono<String> nameMono() {
        return Mono.just("alex")
                .log();
    }

    public Mono<List<String>> namesMonoFlatMap(int stringLen) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLen)
                .flatMap(this::splitStringMono);
    }

    private Mono<List<String>> splitStringMono(String name) {
        String[] split = name.split("");
        return Mono.just(List.of(split));
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> System.out.println("Name is: " + name));

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(name -> System.out.println("Name is: " + name));
    }
}
