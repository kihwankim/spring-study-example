# 프로젝트 안내

## 현재 상태

- 이 저장소는 Redis 큐 예제를 만들기 위한 Spring Boot 애플리케이션의 초기 골격입니다. 아직 큐 생산자, 소비자, API는 구현되지 않았습니다.
- Kotlin 2.3.21, Spring Boot 4.1.1, Java 25, Gradle 9.7.1을 사용합니다.
- 기본 패키지는 `com.example.redisqueueexample`입니다.

## 주요 파일

- `build.gradle.kts`: 플러그인, 의존성, Java 툴체인, 테스트 설정
- `src/main/kotlin/com/example/redisqueueexample/RedisQueueExampleApplication.kt`: 애플리케이션 진입점
- `src/main/resources/application.yaml`: 애플리케이션 이름과 Redis 연결 설정 (`localhost:6379`)
- `src/test/kotlin/com/example/redisqueueexample/RedisQueueExampleApplicationTests.kt`: Spring 컨텍스트 로딩 테스트

## 작업 방법

- 코드는 기존 패키지와 Kotlin 관례에 맞춰 작성합니다. 큐 자료구조나 전달 보장 방식은 현재 코드에 정해져 있지 않으므로, 기능 요구사항에 맞춰 선택합니다.
- Redis 연결 설정을 변경할 때는 로컬 실행과 테스트에 미치는 영향을 확인합니다.
- 변경 범위에 맞는 테스트를 추가하거나 실행합니다. 기본 검증 명령은 `./gradlew test`입니다.
- 애플리케이션 실행 명령은 `./gradlew bootRun`입니다. Redis를 사용하는 기능을 실행할 때는 설정된 Redis 서버가 필요합니다.
- 작업 중 이미 수정된 파일이 있으면 관련 없는 변경을 덮어쓰지 않습니다.
