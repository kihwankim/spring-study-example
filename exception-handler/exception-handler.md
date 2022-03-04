## 1. Exception 처리 방식

### try - catch

- 가장 원시적인 방법
- 코드 가독성과 복잡성 증가
- 비즈니스 로직보다 예외 처리 코드가 더 많아짐

### Exception Handler Resolver 사용하기

- Controller에서 밸상한 Exception을 처리하는 EndPoint 생성
- 대부분 Resolver를 기본적으로 제공하고 있습니다(RestControllerAdvice)

### Request 객체 forward

- HandlerInterceptor

## 2. Exception Handling

### 정의

- Exception처리를 하기 위해서 `HandlerExceptionResolver`를 사용해서 Exception을 처리 합니다
- 4가지 HandlerExceptionResolver존재
- ResponseStatusExceptionResolver: Exception을 특정 HTTP 응답 코드로 저환 해주는 전략
- DefaultHandlerExceptionResolver: 404(NoSuchRequestHandlingMethodException), 400(TypeMissmatchException)와 같이 Spring에서
  기본적인 Exception에 대한 handler가 존재
- SimpleMappingExceptionResolver: web.xml 의 <error-page>등으로 선언한 예외 view지정 전략 입니다
- AnnotationMethodHandlerExcetpionResolver: ControllerAdvice/RestControllerAdvice등 이나 일반 Controller 등에서
  @ExceptionHandler로 Exception 처리 하는 방식 입니다.

### @ExceptionHandler (AnnotationMethodHandlerExcetpionResolver)

- Controller, RestController가 적용된 Bean내에서 발생하는 Exception을 잡아서 처리 해줍니다.
- 주의점: Service Layer에서는 처리를 못해줍니다
    - 대부분 spring application에 call을 날리는 부분은 controller이고, Service에서도 Exception이 발생하더라도 Exception이 Controller에 전파가 됩니다
- @ExceptionHandler를 등록한 Controller에만 처리가 가능
    - 아래 예시에서는 NullPointException을 처리할 수 없습니다

```Java
@RestController
public class TestController {
    @GetMapping("/exception/{value}")
    public String makeNullPointException(@PathVariable("value") Integer value) {
        if (value == 0) {
            throw new NullPointerException();
        }

        return "Good";
    }
}

@RestController
public class TestOtherController {
  @ExceptionHandler(NullPointerException.class)
  public Object nullex(Exception e) {
    System.err.println(e.getClass());
    return "myService";
  }
}
```

### @RestControllerAdvice

- @Controller, @RestController에서 전역적으로 발생하는 Exception을 처리하는 Annotation 입니다
- package 설정을 하게 되면 package 단위로 처리가능 합니다.
    - `@RestControllerAdvice("com.example.demo.test.controller")`

### Request 객체 forward

- 주로 Header값이나 Auth 검사할 때 사용하는 예외 처리 방식입니다

```Java
@Component
public class ExceptionHandlerInterceptor implements HandlerInterceptor { // handler 처리 로직 추가
  @Override // controller 접근전
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    System.out.println("before");
    // header authentication 검사 -> 예외 controller page 변경
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }

  @Override // controller 접근 후
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override // 완료 후
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    System.out.println("complete");
    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }
}


@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    private final ExceptionHandlerInterceptor exceptionHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(exceptionHandlerInterceptor);
    }
}
```

- preHandle에서 예외 케이스(token 정보 불일치 session validate 이슈)에 대해서 redirection할 수 있습니다

cf) Resolver

- 하나의 해결자 입니다.
- view resolver는 matching view file을 찾아주는 역할을 합니다.(String -> .jsp/,html file 찾아 주는 역할)
- Handler Resolver: 예외가 발생했을때 이 예외처를 처리하는 handler를 찾아 줘서 처리해 주는 역할을 합니다
- 위와 같이 문제나 어떤 업무가 있을때 해결을 해주는 해결자 및 확정을 지어주는 존재라고 생각하면 됩니다 