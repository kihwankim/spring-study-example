# 1. Retry

## 1.1 Retry

### 1.1.1 정의

- 일부 exception이 발생시 다시 재시도 하는 오픈소스 라이브러리 입니다
- `@Retryable`를 붙이면 AOP 기반으로 동작

## 1.2 Retry 사용 법

### 1.2.1 Retry Config 설정 등록

```kotlin
@EnableRetry
@Configuration
class RetryConfig

```

```java

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableAspectJAutoProxy(proxyTargetClass = false)
@Import(RetryConfiguration.class)
@Documented
public @interface EnableRetry {

    boolean proxyTargetClass() default false;

}
```

- 위 코드와 같이 `@EnableRetry`를 통해 Retry 가능하게 설정을 해야 합니다
- `@EnableRetry`가 선언되면 `RetryConfiguration` config class를 로드하게 됩니다.
- `RetryConfiguration` class 는 Retry에 필요한 AOP를 정의 하는 class 입니다. 아래(2.1.1)에서 설명할 `AnnotationAwareRetryOperationsInterceptor` 또한 `RetryConfiguration` class 에서 생성됩니다.

### 1.2.2 @Retryable 사용

```kotlin

private val logger = KotlinLogging.logger { }

@Service
class TestCallee {

    @Retryable(backoff = Backoff(delay = 50L, multiplier = 2.0, maxDelay = 1000), maxAttempts = 2, value = [IllegalStateException::class])
    fun errorTest(): Int {
        logger.info("run data")
        throw IllegalStateException()
    }
}

```

- 위와 같이 `@Retryable` 를 선언해주면 default로 최대 3회(첫시도 + 2번 retry)는 실행할 수 있게 됩니다.
- `@Retryable`내부의 `@Backoff`는 retry 를 진행할 때 얼마나 대기를 할지 설정할 수 있게 해줍니다.
    - 위예시는 1번 retry 전에 50ms 대기, 2번째 retry 전 100ms 대기
    - `multiplier`는 retry 회수 증가시 (delay 시간) * (multiplier * retryCount) 만큼 대기하게 됩니다.
    - `maxDelay`는 retry 하기전에 최대로 대기할 수 있는 시간입니다. multiplier로 인해서 backoff wait시간이 증가하게 되는데 이것의 제약을 두는 옵션입니다.
- value 는 retry할 수 있는 Exception class type을 정의할 수 있습니다.
- exclude 옵션을 통해서 retry 하지 않을 Exception class 또한 정의 할 수 있습니다.

# 2. Retry 내부 구조

## 2.1 Retry 설정과 적용

### 2.1.1 AOP 적용

1. AOP 적용 방식

```java
public class AnnotationAwareRetryOperationsInterceptor implements IntroductionInterceptor, BeanFactoryAware {
    //.. codes
}
```

- `AnnotationAwareRetryOperationsInterceptor`는 `IntroductionInterceptor` interface를 구현한 클래스 입니다.
- `IntroductionInterceptor` interface는 `MethodInterceptor`(`Advice`를 상속받은 interface)를 상속받는 interface 입니다.
- 결론적으로 `IntroductionInterceptor` interface는 AOP를 적용할 때 Advice를 담당하게 됩니다.
- `MethodInterceptor` interface는 `Object invoke(MethodInvocation invocation) throws Throwable` method를 통해서 추가 기능(@Around 와 동일)을 정의 할 수 있습니다.
- `invoke` method를 통해서 retry 로직을 추가한 Advice는 `RetryConfiguration` class를 통해서 생성되고, Bean으로 등록이 됩니다.
- 우리는 위 내용을 통해서 `@Retryable` 붙은 method를 호출할 경우 `AnnotationAwareRetryOperationsInterceptor`에 정의된 invoke 함수를 호출하는 것을 알게 되었습니다.

![AOP 적용.png](assets/2_AOP적용.png)

- 현재 위 사진을 보면 `invoke` method가 호출 되면 `getDelegate` method를 호출 합니다.

[//]: # (- `@Retryable` annotation은 한 곳에 설정되는 것이 아니라 여러 곳에 선언하게 됩니다. 결론적으로 하나의 method에 여러개의 @Retryable이 붙게 되면)

- `AnnotationAwareRetryOperationsInterceptor` class 내부에서는 retry 횟수와 같은 옵션 정보를 담고있는 `@Retryable`을 가져오고 있습니다.
- 가져온 `getStatelessInterceptor` 라는 method를 호출 합니다.

```java
class AnnotationAwareRetryOperationsInterceptor implements IntroductionInterceptor, BeanFactoryAware {

    // codes...

    private MethodInterceptor getStatelessInterceptor(Object target, Method method, Retryable retryable) {
        RetryTemplate template = createTemplate(retryable.listeners());
        template.setRetryPolicy(getRetryPolicy(retryable));
        template.setBackOffPolicy(getBackoffPolicy(retryable.backoff()));
        return RetryInterceptorBuilder.stateless().retryOperations(template).label(retryable.label())
                .recoverer(getRecoverer(target, method)).build();
    }

    // codes...

}
```

- `getStatelessInterceptor` method는 `@Retryable`에 정의된 옵션들을 `RetryPolicy`, `BackOffPolicy` 라는 객체에 넣어서 정의 합니다.
- 그리고 exception 발생시 실제 retry를 진행하는 `RetryTemplate` class를 생성하고 `RetryPolicy`, `BackOffPolicy`를 주입 해줍니다.

## 2.2 RetryTemplate

### 2.2.1 정의

- `RetryTemplate`는 주입받은 `RetryPolicy`, `BackOffPolicy` 객체를 통해서 실제 retry 로직을 실행하는 class 입니다.

### 2.2.2 Retry Flow

```java

public class RetryTemplate implements RetryOperations {
    protected <T, E extends Throwable> T doExecute(RetryCallback<T, E> retryCallback,
                                                   RecoveryCallback<T> recoveryCallback, RetryState state) throws E, ExhaustedRetryException {

        // retry policy 와 retry backoff policy 가져오기
        try {

            while (canRetry(retryPolicy, context) && !context.isExhaustedOnly()) { // retry 가능 여부 검사

                try {
                    lastException = null;
                    return retryCallback.doWithRetry(context); // 비즈니스 로직 호출
                } catch (Throwable e) { // 에러 발생

                    lastException = e;

                    try {
                        registerThrowable(retryPolicy, state, context, e); // 에러 발생 관련 정보 등록 -> 몇회 error 발생했는지 여부 저장
                    } catch (Exception ex) {
                        throw new TerminatedRetryException("Could not register throwable", ex);
                    } finally {
                        doOnErrorInterceptors(retryCallback, context, e);
                    }

                    if (canRetry(retryPolicy, context) && !context.isExhaustedOnly()) { // retry 가능 여부 검색
                        try {
                            backOffPolicy.backOff(backOffContext); // backoff 조건에 대해서 실행(일부 시간 동안 대기)
                        } catch (BackOffInterruptedException ex) { // backoff error handle
                            lastException = e;
                            throw ex;
                        }
                    }

                }
            }

            exhausted = true;
            return handleRetryExhausted(recoveryCallback, context, state);
        } catch (Throwable e) {
            throw RetryTemplate.<E>wrapIfNecessary(e); // 예외 처리
        } finally {
            // retry에 관련된 자원 삭제
        }

    }
}
```

1. 처음에 retry 정책과 backoff 정책을 가져옵니다
2. retry 가능 여부 검사 -> 처시도, 몇번째 시도인지 등을 검사하는 로직이 들어있습니다
3. `retryCallback.doWithRetry(context)` method를 통해서 비즈니스 로직 호출 합니다.
4. 에러 발생시 에러 발생 관련 정보 등록합니다. 결론적으로 몇 회의 error 발생했는지 여부 저장합니다.
5. backoff 조건이 있을 경우 backoff 조건을 실행 합니다 -> 일부분 시간동안 대기를 하게 됩니다
6. `2` 번 부터 다시 실행하고, 만약 error 횟수 초과시 에러를 반환 합니다. 
