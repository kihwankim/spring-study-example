## 1 정의
### WebFlux 등장 배경
- 적은 쓰레드로 동시에 처리를 제어하기 위해서 등장
- 적은 하드웨어 리소스로 확장하기 위해서 non blocking 기술이 필요
- 처음 등장했을 때 Servlet으로 Non Blocking을 구현하려면 Filter와 같은 다른 동기 처리에서 NonBlocking을 사용하기 어려웠었습니다(Servlet 3.1 버전)
- Netty와 같은 NonBlocking 서버를 적극적으로 응요할 수 있는 API 필요 -> 그렇기 때문에 WebFlux가 탄생
- Java8에서부터 람다가 추가되었으므로 Annotation방식 API 대신 함수형 API interface정의 하는 방식이 가능 -> 선택의 폭의 증가

### Reactive 정의

- 정의: 변화에 반응하는 것을 중심으로 두고 만든 프로그래밍 모델을 의미
- 추가설명: NonBlocking작업을 기다리다가 완료가 되면 그때 반응한다고 생각하면 편합니다
- Back Pressure: 현재 컴포넌트가 부하를 이겨낼 수 있도록 현재 과부하 상태인지 아닌지를 publiser(공급자)에게 알려주는 것을 의미합니다.
  - http 서버(Subscriber 역할)가 데이터를 data repository(Publiser)에 요청을 한다고 가정하겠습니다
  - 만약 http server가 100개 까지 수용가능하지만 data repository 가 200개의 데이터를 가져오면 부하가 증가되고 100개를 버리던지 아니면 queue size를 증가시켜서 해결해야합니다
  - 하지만 queue size를 증가시키면 out of memory 문제와, data 를 버리게 되면 데이터 유실 이슈가 발생합니다.
  - 그러므로 http server가 100개를 수용가능하고 현재 20개의 데이터가 queue에 쌓여 있는 상태에서 data repository에 80개의 데이터를 요청하는 형식을 back pressure라고 합니다.
  - 수용가능한 정도면 요청하는 거라고 생각합니다
- 여기서 publiser의 속도를 줄일 수 없는경우(무조건 publiser는 200개의 데이터를 반환한느 경우)
  - 버퍼에 담을지 아니면 데이터를 날릴지, 실패로 처리할지 결정해야 합니다

### Reactive API
- Reactor는 Spring WebFlux가 선택한 reactive lib
- Reactor는 Mono와 Flux API 타입이 제공
  - Mono: 0 ~ 1개의 데이터 시퀀스
  - Flux: 0 ~ N개의 데이터 시퀀스
- Reactor는 reactive stream lib이기 때문에 모든 연산자는(Mono, Flux) non blocking과 back pressure를 지원 합니다
- WebFlux는 기본적으로 Reactor를 reactive lib로 사용하지만, 다른 reactive lib써도 reactive stream으로 상호작용할 수 있습니다
  - WebFlux API의 일반적인 룰은 순수한 Publiser를 입력받아 내부적으로 reactor type을 맞추고, 이걸 사용해서 Flux, Mono를 반환 합니다.
  - 다른 Reactive Lib를 사용한다면 출력 형태를 맞춰야 합니다(이해 못함) -------->>
- WebFlux는 Kotlin 코루틴 API와도 상요할 수 있습니다

### Reactive Lib
- Spring-Webflux는 reactor-core로 비동기 로지과 reactive stream을 구현합니다
- WebFlux API는 일반적으로 Flux, Mono를 반환
- Reactive Stream중 일부는 Publiser든 입력으로 받을 수 있습니다
- Annotation으로 선언한 Controller에서 WebFlux가 Application에서 사용하는 Reactive Lib 타입으로 맞춰줍니다(이해 못함) -------->>
  - reactive lib나 다른 비동기 타입을 플로그인 처럼 받아주는 ReactiveAdapeterRegistery가 존재해서 가능
  - ReactiveAdapeterRegistery는 RxJava와 CompleteFuture를 지원하며, 다른 lib도 등록 가능

### Programming Models
- `Spring-web` 모듈에 있는 webflux는 아래와 같은 개념을 가지고 있습니다
  - 여러 서버에서 지원하기 위해서 HTTP 추상화
  - Reactive Stream 어댑터
  - Servlet API와 상응하는 Core WebHandler API
    - WebHandler: http 통신에서 아래와 같은 기능을 제공해주는 추상화 handler 입니다 
      - UserSession과 Session Attribute
      - Request Attribute
      - Locale, Principal Resolver
      - form-data 파싱, 캐시 조회
      - Multipart 데이터 추상화
- Spring WebFlux는 2가지 Programming Model을 지원 합니다

1. Annotation Controllers
- Spring MVC와 동일하게 `Spring-web` module에 있는 같은 Annotation을 사용
- Spring MVC와 WebFlux controller 모두 Reactive(Reactor, RxJava)리턴 타입을 지원(두가지의 구분에 어려움 존재)
- WebFlux에서는 @RequsetBody로 Reactive 인자를 받을 수 있다
- Annotataion으로 의도(Get/Post/Delete/Put, uri ...etc)를 선언해서 Application 처음부터 끝까지 전부 제어 

2. Functional Endpoints
- 경량화된 Lambda기반 함수형 프로그래밍 모델
- 요청을 라우팅해주는 조그마한 라이브러리나 유틸리티의 모음
- API 요청에 대해서 Callback을 등록하는 방식

### WebFlux Server
- Tomcat, Jetty, Servlet 3.1 + Container, Netty(Servlet 기반이 아닌 경우), Undertow에서도 잘동작 합니다
- Spring 설정과 WebFlux 구조를 조립해 적은 코드로 쉽게 Application 실행 가능(@EnableWebFlux 추가)
- Spring boot에서는 WebFlux Starter가 이 단계를 자동화 해줍니다 -> default Netty 사용