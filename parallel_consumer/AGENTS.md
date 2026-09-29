# Repository Guidelines

## Project Structure & Module Organization

This is a single-module Kotlin/Spring Boot application built with Gradle. Production code lives under `src/main/kotlin/com/example/parallel_consumer`. Keep HTTP endpoints in `api`, Kafka integration and infrastructure in `infra`, consumer setup and handlers in `consumer`, persistence configuration in `persistence`, and shared domain utilities in `domain`. Runtime configuration is split across `src/main/resources/application.yaml`, `kafka.yaml`, `rds.yaml`, and `monitoring.yaml`. Tests mirror the production package hierarchy under `src/test/kotlin`.

## Build, Test, and Development Commands

Use the checked-in Gradle wrapper; Java 25 is required by the configured toolchain.

- `./gradlew clean build` compiles the application, runs all tests, and creates the executable artifact.
- `./gradlew test` runs the JUnit 5 test suite.
- `./gradlew bootRun --args='--spring.profiles.active=local'` starts the service with the H2-backed local profile.
- `./gradlew tasks` lists additional build and Spring Boot tasks.

Local Kafka configuration expects brokers on ports `9092`, `9093`, and `9094`. The default `dev` profile expects MySQL at `localhost:3306/test`; avoid committing real credentials or environment-specific secrets.

## Coding Style & Naming Conventions

Follow idiomatic Kotlin with four-space indentation, trailing commas in multiline declarations, and expression bodies where they improve clarity. Use `PascalCase` for classes, `camelCase` for functions and properties, and uppercase underscore-separated names for constants. Package names remain lowercase and should reflect the existing feature/layer structure. Prefer constructor injection and Spring annotations on configuration or boundary classes. No formatter or linter is currently configured, so use IntelliJ's standard Kotlin formatting and keep imports optimized.

## Testing Guidelines

Tests use JUnit 5, Kotlin test support, and Spring Boot test starters. Name test classes `*Tests` for application-level suites or `*Test` for focused units, and give test methods behavior-oriented names, using backticks when useful. Add tests beside the corresponding package and use the `test` or `integration-test` profile when infrastructure should be replaced by H2/local settings. Run `./gradlew test` before opening a pull request. No coverage threshold is enforced; cover new behavior and regressions meaningfully.

## Commit & Pull Request Guidelines

Recent history uses Conventional Commit-style prefixes such as `feat:`, `refactor:`, and `chore:` followed by short descriptions. Keep each commit focused and use an imperative summary. Pull requests should explain the change and verification performed, link relevant issues, and call out configuration, Kafka topic, database, or API changes. Include sample requests or screenshots only when endpoint or UI behavior benefits from them.
