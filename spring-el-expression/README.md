# SpEL 문법

## 1. 개념

### 정의

- Spring Expression Language(SpEL)로 불립니다
- run time에 객체 그래프를 쿼리하고를 지원하고, 객체를 조작할 수 있는 강력한 표현식 언어 입니다
    - cf) 파라미터 참조 와 같이 그래프화 한것들 jpa에 Entity graph와 비슷하다고 생각하면 됩니다
- 문자열 template과 같은 곳에 주로 많이 사용됩니다
- Spring 내부에 프로젝트로도 존재하지만, 필요하다면 parser와 같은 여러 클래스를 생성하거나 infra 설정을 통해서 SpEL을 독립적인 표현 언어 처럼 사용할 수 있습니다

### 제공 기능들

1. 리터럴 표현식 (Literal Expression)
2. Boolean과 관계형 Operator (Boolean and Relational Operator)
3. 정규 표현식 (Regular Expression)
4. 클래스 표현식 (Class Expression)
5. 프로퍼티, 배열, 리스트, 맵에 대한 접근 지원 (Accessing properties, arrays, lists, maps)
6. 메서드 호출 (Method Invocation)
7. 관계형 Operator (Relational Operator)
8. 할당 (Assignment)
9. 생성자 호출 (Calling Constructors)
10. Bean 참조 (Bean References)
11. 배열 생성 (Array Contruction)
12. 인라인 리스트/맵 (Inline List/Map)
13. 삼항 연산자 (Ternary Operator)
14. 변수 (Variables)
15. 사용자 정의 함수 (User defined functions)
16. 컬렉션 투영 (Collections Projection)
17. 컬렉션 선택 (Collections Selection)
18. Template 표현식 (Templated expression)

## 2. 구성 요소

### ExpressionParser

```kotlin
class Test {

    fun expressionTest() {
        val parser: ExpressionParser = SpelExpressionParser()
        val exp: Expression = parser.parseExpression("'Hello World'")
        val message = exp.value as String
    }
}
```

- 표현 문자열(expression string)을 파싱하는 책임을 가지는 interface 입니다
- Expression interface는 `ExpressionParser`를 통해서 파싱된 표현 문자열을 평가 합니다
- `parser.parseExpression(Str)`를 통해서 평가를 진행하게 될 경우, 평가 과정에서 잘못된 구문이 들어가 있을 경우 `ParseException`에러를 뛰우게 됩니다.
- `exp.getValue(obj)` method를 통해서 평가를 진해하게 될 경우, 평과 과정에서 잘 못 된 부분이 있을 경우 `EvaluationException`이 발생하게 됩니다

```kotlin
class Test {

    fun expressionTest() {
        val parser: ExpressionParser = SpelExpressionParser()
        val exp: Expression = parser.parseExpression("new String('hello world').toUpperCase()")
        val message = exp.getValue(String::class.java)
    }
}
```

- 위와 같이 getValue method를 호출할 때 Type를 정의한다면 해당 타입으로 반환하게 됩니다

### EvaluationContext

1. 정의

- Root 객체의 속성, 메서드, 필드를 확인하고, 유형을 변환(SPeL 문법을 파싱해서 결과를 반환하는 변환 과정)을 수행하는 데 사용되는 되는 식 평가에 주로 이용됩니다.
- `SimpleEvaluationContext` 과 `StandardEvaluationContext` 등이 존재 합니다

2. SimpleEvaluationContext

- SpEL 언어 문법의 일부분만을 사용한 Context 입니다
- 생성자와 java type 참조 등은 제외되어 있습니다

3. StandardEvaluationContext

- 객체를 조작하려고 relection을 이용하는 기능이 추가된 구현체 입니다
- 성능 향상을 위해서 `TypedValue` 라는 객체에 Method, Field, Constructor 객체(객체의 시그니처 정보)를 `TypeDescriptor` 타입으로 캐싱해서 사용하게 됩니다


4. Root 객체 정의

- SpEL 표현식에서 사용되는 객체를 의미 합니다
- SpEL 표현식에서는 Root 객체의 시그니처 정보를 이용해서 파리미터 정보와 같은 정보를 동적으로 가져올 수 있습니다

```kotlin
fun expresionTest() {
    // Create and set a calendar
    val c = GregorianCalendar()
    c.set(1856, 7, 9)

    // The constructor arguments are name, birthday, and nationality.
    val tesla = Inventor("Nikola Tesla", c.time, "Serbian")

    val parser = SpelExpressionParser()

    var exp = parser.parseExpression("name") // Parse name as an expression
    val name = exp.getValue(tesla) as String
    // name == "Nikola Tesla"

    exp = parser.parseExpression("name == 'Nikola Tesla'")
    val result = exp.getValue(tesla, Boolean::class.java)
    // result == true
}
```

```kotlin
fun expresionTest() {
    // Create and set a calendar
    val c = GregorianCalendar()
    c.set(1856, 7, 9)

    // The constructor arguments are name, birthday, and nationality.
    val tesla = Inventor("Nikola Tesla", c.time, "Serbian")

    val parser = SpelExpressionParser()

    var exp = parser.parseExpression("name") // Parse name as an expression

    val context = StandardEvaluationContext(tesla)

    val name = exp.getValue(context) as String
    // name == "Nikola Tesla"

    exp = parser.parseExpression("name == 'Nikola Tesla'")
    val result = exp.getValue(context, Boolean::class.java)
    // result == true
}
```

- 위 예시에서 Inventor 라는 객체가 Root를 바로 직접 `getValue method`에 전달해 reflection을 이용하여 signature 정보를 수집합니다. 그리고 시그니처 정보를 통해서 원하는 결과를 얻는 방법을 가집니다
- StandardEvaluationContext를 이용하게 되면, 미리 캐싱된 signature 정보를 이용하는 방식이 있습니다
- 만약 Root Context가 계속해서 변경되는 경우 첫번 째 방식을 사용하고, 내부 데이터 변경 없이 반복적으로 사용될 경우 2번 째 방식을 사용하면 성능적인 이득을 취할 수 있습니다.

## 참고 학습 자료

- [공식문서](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions)