## 1. 동기 VS 비동기

### 동기

1. 정의: 반환 값 및 결과물이 반환되면 바로 처리해야하는 것을 의미

### 비동기

1. 정의: 반환 값을 바로 처리하지 않아도 되는 것 Java에서는 Future type과 js에선는 axios type을 사용

## 2. Blocking VS NonBlocking

### Blocking

1. 정의: 외부 시스템/다른 주체에 처리 요청후 처리가 완료(끝날때)될때 까지 무조건 대기 하는 것

### NonBlocking

1. 정의: 외부 시스템 및 thread에 처리 요청후 처리가 완료될때 까지 대기 하지 않고 다른 업무를 처리할 수 있는 것

## 비동기 nonblocking 프로그래밍

### Aysnc를 Future 사용할 경우

```Java
public class FutureMain {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double> future = executorService.submit(new Callable<Double>() {
            public Double call() {
                return someLongComputation();
            }
        });
        doSomethingElse();
        try {
            Double result = future.get(1, TimeUnit.SECONDS); // <--- 블록 방지 
        } catch (InterruptedException e) {
            // handle e 
        } catch (ExecutionException e) { // handle e 
        } catch (TimeoutException e) { // handle e 
        }
    }

    private static Double someLongComputation() {
        // do something 
        return 1d;
    }

    private static void doSomethingElse() {
        // do something else 
    }
}
```

1. 정의
- Java 5부터 미래 시점에 결과를 얻을 수 있도록 Future 인터페이스를 지원합니다
- NonBlocking을 사용할 경우 위예시와 같이 ExecutorService를 통해서 다른 쓰레드에 작업을 요청하고, Future type을 반환 받습니다. 그리고 ExecutorService가 처리가 완료될 경우 get 함수를 통해서 반환 정보를 얻을 수 있습니다.

2. 한계점
- get method를 통해서 데이터를 가져올 때 다른 thread가 작업을 완료하고 데이터를 반환하면 정상 처리가 되지만 처리가 되어 있지 않을 경우 `ExecutionException`기 빌셍하게 됩니다.
- 결과 적으로 isDone한수를 호출해서 while문으로 데이터가 반환될때까지 기다려야 하는 blocking sync 형태가 됩니다
- 그리고 while문으로 기다리게 되면 busy wait로 인해 리소스를 사용하게 되니까 더 많은 오버헤드가 발생할 수 있습니다
- 위와 같이 기본적으로 isDone, isCanceled와 같이 기본 상태 체크만 가능합니다
- 비즈니스 로직을 추가할 수 없습니다 -> 반환된 결과를 합치는 작업
- 요약: 비동기 연산들을 하나로 합치거나 에러 핸들링 할 수 없었음

### CompletableFuture로 비동기 로직 변경
1. 요구사항
- 0.7초 정도 sleep == DB접근, 엄청 오래걸리는 알고리즘, image S3와 같은 파일 서버로 upload, 외부 API 호출
- index 값 반환(API 결과 반환)
- index 값들을 모두 List에 저장해서 반환(API 결과 전체 리스트 반환)

2. ComletableFuture 객체 간단한 기능과 설명
- Java 8에 처음 추가
- 비동기 작업을 조합할 수 있는 메소드를 정의한 `CompletionStage` 인터페이스가 들어왔고 `CompletionStage`의 구현체중 1개 입니다. 
- Future interface를 구현한 구현체 중 1개
- 기본적으로 사용하는 방법
  - 로직
    - CompletableFuture 객체 생성 후 comsumer(client/요청자/Future 객체를 참조하는 사람)에게 넘긴다
    - Future는 complete method를 호출해서 Comsumer쪽으로 전달
    - Comsumer는 get method를 통해서 필요한 값을 전달 받음
    - get method를 호출한 comsumer는 데이터를 받을 때 까지 blocking이 됩니다
  - 위코드에서는 0.5초 이후에 client는 "Hello"라는 값을 반환 받습니다
  - 만약 Future실행 중 예외 발생하면 ExecutionException, 실행중인 Thread에 Interrupt가 발생하면 InterruptedException가 발생합니다


```Java
class Test {
  public Future<String> calculateAsync() throws InterruptedException {
    CompletableFuture<String> completableFuture = new CompletableFuture<>();

    Executors.newCachedThreadPool().submit(() -> {
      Thread.sleep(500);
      completableFuture.complete("Hello");
      return null;
    });

    return completableFuture;
  }
}
```

- cancel 방식
  - Future 내부에서 cancel을 호출 하게 되면 `CancellationException`을 반환

- 연산로직 추가한 경우(runAsync, supplyAsync)
  - 정의: Runnable, Supplier 인터페이스를 받아서 ForkJoinPool에서 로직을 처리합니다
  - ForkJoinPool은 서버 PC의 core 수만큼 미리 생성해서 재사용 합니다
  - 비동기 처리 결과 handler
    - 정상 처리
      - thenApply: 정상 처리 결과에 추가 연산 실행
      - thenAccept을: Future로 부터 인자를 받지만, 만약 아무 값도 반활 하기 싫은 경우
      - thenRun: Future로부터 인자를 받지 않고, 아무 값도 반환 하지않는 경우

```Java
CompletableFuture<String> completableFuture
  = CompletableFuture.supplyAsync(() -> "Hello");
 
CompletableFuture<String> future = completableFuture
        .thenApply(s -> s + " World");
assertEquals("Hello World", future.get());

CompletableFuture<Void> future = completableFuture
        .thenAccept(s -> System.out.println("Computation returned: " + s));
future.get();


CompletableFuture<Void> future = completableFuture
        .thenRun(() -> System.out.println("Computation finished."));
future.get();
```
- 독립된 Future를 조합하는 경우
  - thenCombine: 1개의 Future 인자를(1번 째 인자) 받아서 2번째 인자로 결과를 처리 하는 방식을 정의
  - thenAcceptBoth: 2개의 Future 인자에 대해서 처리 결과를 반환하지 않는 경우 사

```Java
CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
    .thenCombine(
        CompletableFuture.supplyAsync(() -> " World"), 
        (s1, s2) -> s1 + s2)
    );
 
assertEquals("Hello World", completableFuture.get());

CompletableFuture future = CompletableFuture.supplyAsync(() -> "Hello")
        .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"), 
            (s1, s2) -> System.out.println(s1 + s2));
}
```

- Future 병렬 실행(NonBlocking)
  - 3개의 Future가 있을 경우 병렬로 실행 후 3개가 모두 끝날때 까지 기다렸다가 처리 결과 반환
  - CompletableFuture.allOf: 여러개의 future type이 병렬로 처리하고 결과가 반환(done 상태)될 때까지 대기하고, Void type을 반환
    - 추가 결과 연산을 할 수 없음 -> 대신 allOf 인자로 받은 future 1, 2, 3객체에 대해서 get method로 결과를 반환 후 처리 가능
  - join method와 java8에 추가된 stream api 응용하기
    - join은 complete가 되지 않으면 unchecked exception 발생
    - get과 join 차이점
      - get은 check exception 발생
      - join은 unchecked exception 발생(`CompletionException.class`)

```Java
// allOf 방식
CompletableFuture<String> future1  
  = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future2  
  = CompletableFuture.supplyAsync(() -> "Beautiful");
CompletableFuture<String> future3  
  = CompletableFuture.supplyAsync(() -> "World");
 
CompletableFuture<Void> combinedFuture 
  = CompletableFuture.allOf(future1, future2, future3);

combinedFuture.get();
 
assertTrue(future1.isDone());
assertTrue(future2.isDone());
assertTrue(future3.isDone());

// join 방식
String combined = Stream.of(future1, future2, future3)
.map(CompletableFuture::join)
.collect(Collectors.joining(" "));

assertEquals("Hello Beautiful World", combined);
```
- Error Handling
  - throw/catch 방식이 아닌 handle 메소드를 이용하여 에러 핸들링을 진행
  - throw하를 위해서는 completeExceptionally을 사용해서 추가 예외로 변경이 가능합니다
  - 

```Java
String name = null;

CompletableFuture<String> completableFuture =  CompletableFuture.supplyAsync(() -> {
        if (name == null) { 
            throw new RuntimeException("Computation error!");
        }
        return "Hello, " + name;
    })}).handle((s, t) -> s != null ? s : "Hello, Stranger!");

assertEquals("Hello, Stranger!", completableFuture.get()); // success

CompletableFuture<String> completableFuture = new CompletableFuture<>();
  
completableFuture.completeExceptionally(new RuntimeException("Calculation failed!"));

completableFuture.get(); // ExecutionException
```
```Java
class Test {
    CompletableFuture<List<String>> cf = CompletableFuture
            .supplyAsync(this::process)
            .exceptionally(this::getFallbackListOfStrings) // Here you can catch e.g. {@code join}'s CompletionException
            .thenAccept(this::processFurther);
}
```

- CompleteFuture 비동기 방식은 postfix로 Async가 붙은 메소드 입니다 -> 다른 Thread 에서 수행한다는 의미
- CompletbleFuture class는 Supply를 인자로 받아서 반환 값을 CompleleteFuture 객체로 감싸서 반환 해주는 함수도 존재합니다
- CompletableFuture는 Core 개수만큼의 ForkJoinPool를 이용해서 Nonblocking Async처리를 해줍니다

```Java
 class Test {
    
    public List<Integer> asyncWithWait(List<Integer> indexs) {
        List<CompletableFuture<Integer>> priceFutures = indexs.stream()
                .map(index -> CompletableFuture.supplyAsync(() -> {
                    sleepTest(700, index);
                    return index;
                }))
                .collect(Collectors.toList());

        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
```