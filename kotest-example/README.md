## Kotest

### Junit의 단점

- 한눈에 given when then 구분 어려움
- 중복 코드 많음 -> 이부분은 하위에 `중복 코드 제거` 부분에서 언급하겠습니다.
- 테스트 스타일이 한정적 -> 단위 테스트 특화
- Junit AssertJ, Mockito를 사용하면 Mocking이나 Assertions 과정에서 kotlin DSL 활용 불가

### kotest 장점

- nested test code의 가독성을 가져올 수 있음
- DSL(Given( When( Then() ) )) 과같은 구성으로 좀더 명확하게 구분을 지을 수 있음 -> 가독성 증가
- Kotlin는 멀티 플랫폼이므로 다양한 플랫폼의 스타일이 가능
    - 당양한 test layout 제공
    - ex) 스칼라, 루비 ...etc

### kotest 단점

- mockk을 사용할 경우 속도가 느리다

### 의존성 추가 방법

```kotlin
testImplementation("io.kotest:kotest-runner-junit5:5.4.2") // kotlin junit 처럼 쓸 수 있는 Spec 들이 정의 됨
testImplementation("io.kotest:kotest-assertions-core:5.4.2") // shouldBe... etc 와같이 Assertions 의 기능을 제공
testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2") // spring boot test 를 위해서 추가
```

### FunSpec

```kotlin
class CalFunSpec : FunSpec({
    test("1과 2를 더하면 3이 반환된다") {
        val stub = Calculator()

        val result = stub.calculate("1 + 2")

        result shouldBe 3
    }

    context("enabled test run") {
        test("test code run") { // 실행
            val stub = Calculator()

            val result = stub.calculate("1 + 2")

            result shouldBe 3
        }

        xtest("test code not run") { // 실행 하지 않음
            val stub = Calculator()

            val result = stub.calculate("1 + 2")

            result shouldBe 3
        }
    }

    xcontext("disabled test run") { // 하위 모두 미 실행
        test("test code run but outer context is disabled") { // 미실행
            val stub = Calculator()

            val result = stub.calculate("1 + 2")

            result shouldBe 3
        }

        xtest("test code not run") { // 미 실행
            val stub = Calculator()

            val result = stub.calculate("1 + 2")

            result shouldBe 3
        }
    }
})
```

- test 뒤에 String 값으로 test code에 대한 설명을 추가할 수 있습니다
- 필드 변수 사용이 불가능하므로 함수 테스트에 주로 사용 됩니다
- junit에 `@Disabled` 와 같이 `xcontext`나`xtest`를 통해서 test code를 실행에서 제외할 수 있습니다.

### Describe Spec

```kotlin
class CalDescribeSpec : DescribeSpec({
    val stub = Calculator()

    describe("calculate") {
        context("식이 주어지면") {
            it("해당 식에 대한 결과 값이 반환 된다") {
                calculations.forAll { (expression, data) ->
                    val result = stub.calculate(expression)

                    result shouldBe data
                }
            }
        }
    }
})
```

- spring 진영에서는 BDD(`given`, `when`, `then`) 쓰고 있고 Ruby나 JS에서도 이와 비슷하게  `describe`, `it` 키워드를 사용해서 test code를 작성할 수 있습니다(DCI(Describe, Context, It) layout 지원)
- 위 code에서 context는 생략 해도 됩니다.
- `FunSpec`과 동일하게 `xdescribe`와 `xit`을 사용하면 해당 case는 실행할 필요가 없습니다.

### Behavior Spec

```kotlin
class CalBehaviorSpec : BehaviorSpec({
    val stub = Calculator()

    Given("calculator") {
        // before Each 라고 생각 하기
        val expression = "1 + 2"

        When("1과 2를 더한면") {
            val result = stub.calculate(expression)
            Then("3이 반환 된다") {
                result shouldBe 3
            }
        }

        When("1 + 2 결과 와 같은 String 입력시 동일한 결과가 나온다") {
            val result = stub.calculate(expression)
            Then("해당 하는 결과값이 반환된다") {
                result shouldBe stub.calculate("1 + 2")
            }
        }
    }
})
```

- BDD 스타일의 test code를 제공합니다
- 우리가 아는 `given`, `when`, `then`을 제공합니다
- `xgiven`, `xwhen`, `xthen` 을 통해서 test code disable 할 수 있다

### AnnotationSpec

```kotlin
class AnnotationSpecExample : AnnotationSpec() {

    @BeforeEach
    fun beforeTest() {
        println("Before each test")
    }

    @Test
    fun test1() {
        1 shouldBe 1
    }

    @Test
    fun test2() {
        3 shouldBe 3
    }
}
```

- 우리가 사용하는 Junit과 가장 비슷한 Spec 입니다
- kotlin junit test code 를 kotest로 마이그레이션 할 때 사용하면 가장 편리하게 사용할 수 있습니다

### Kotest가 Junit 보다 중복 코드가 적은 이유

```kotlin
class CalBehaviorSpec : BehaviorSpec({
    val stub = Calculator()

    Given("calculator") {
        // before Each 라고 생각 하기
        val expression = "1 + 2"

        When("1과 2를 더한면") {
            val result = stub.calculate(expression)
            Then("3이 반환 된다") {
                result shouldBe 3
            }
        }

        When("1 + 2 결과 와 같은 String 입력시 동일한 결과가 나온다") {
            val result = stub.calculate(expression)
            Then("해당 하는 결과값이 반환된다") {
                result shouldBe stub.calculate("1 + 2")
            }
        }
    }
})
```

```java
class CalBehaviorSpec {
    Calculator stub = Calculator();
    String expression;

    @BeforeEach() {
        this.expression = "1 + 2";
    }

    void calculator_test() {
        // given

        // when
        int result = stub.calculate(expression);

        // then
        assertThat(result).isEqualTo(3);
    }

    void addOneTowResultStringSame_test() {
        // given

        // when
        int result = stub.calculate(expression);

        // then
        assertThat(result).isEqualTo(stub.calculate("1 + 2"));
    }
}
```

- 위 java와 kotlin test code는 동일 합니다.
- Junit인 경우 `@BeforeEach`를 통해서 모든 test code의 중복로직을 실행 할 수 있습니다.
- 그리고 만약 특정 test code에서 `@BeforeEach`가 달라지는 경우 Test Class를 분리 하거나, `@Nested` class를 정의하고 `@BeforeEach` 를 추가 정의 해야 하는 한계가 존재합니다(아래 코드 참고)
- kotest인 경우 `Given("")` 하위에 작성한 Code는 `Given` 하위에 존재하는 `When` `Then` 모두 사용할 수 있으므로 `@BeforeEach`와 동일한 결과를 가져다 주고, 위와 같이 간결하게 구현할 수 있습니다

```kotlin
class CalBehaviorSpec : BehaviorSpec({
    val stub = Calculator()

    Given("calculator") {
        // before Each 라고 생각 하기
        val expression = "1 + 2"

        When("1과 2를 더한면") {
            val result = stub.calculate(expression)
            Then("3이 반환 된다") {
                result shouldBe 3
            }
        }

        When("1 + 2 결과 와 같은 String 입력시 동일한 결과가 나온다") {
            val expression1 = "1 + 3"
            val expression2 = "2 + 4"

            val result1 = stub.calculate(expression)
            val result2 = stub.calculate(expression1)
            val result3 = stub.calculate(expression2)

            Then("해당 하는 결과값이 반환된다") {
                result1 shouldBe stub.calculate("1 + 2")
                result2 shouldBe stub.calculate("1 + 3")
                result3 shouldBe stub.calculate("2 + 4")
            }

            Then("상수 값 비교") {
                result1 shouldBe 3
                result2 shouldBe 4
                result3 shouldBe 6
            }
        }
    }
})
```

```java
class CalBehaviorSpec {
    Calculator stub = Calculator();
    String expression;

    @BeforeEach() {
        this.expression = "1 + 2";
    }

    void calculator_test() {
        // given

        // when
        int result = stub.calculate(expression);

        // then
        assertThat(result).isEqualTo(3);
    }

    @Nested
    class NestedClass {
        String expression1;
        String expression2;

        int result1;
        int result2;
        int result3;

        @BeforeEach() {
            this.expression1 = "1 + 3";
            this.expression2 = "2 + 4";

            this.result1 = stub.calculate(expression);
            this.result2 = stub.calculate(expression1);
            this.result3 = stub.calculate(expression2);
        }

        void addOneTowResultStringSame_test() {
            // given

            // when

            // then
            assertThat(result1).isEqualTo(stub.calculate("1 + 2"));
            assertThat(result2).isEqualTo(stub.calculate("1 + 3"));
            assertThat(result3).isEqualTo(stub.calculate("2 + 4"));
        }

        void constVal_test() {
            // given

            // when

            // then
            assertThat(result1).isEqualTo(stub.calculate(3));
            assertThat(result2).isEqualTo(stub.calculate(4));
            assertThat(result3).isEqualTo(stub.calculate(6));
        }
    }
}
```

- 위 java, kotlin code 동일합니다.
- kotlin은 `Nested class` 선언 없이 바로 `Then` 문장을 공통 로직을 작성하면 되었지만, Java는 `@Nested` class를 생성해서 추가로 `@BeforeEach`를 정의 해야지 공통 로직을 세분화 할 수 있습니다.
- 중복을 회피 하기 위해서 `@Nested` class를 생성하는 방법 밖에 없으므로, 가독성과 간결성이 떨어질 수 밖에 없습니다
- kotest 는 DSL형태로 When 하위에 Then을 여러개 추가해서 처리할 수 있으므로 간결성을 증대시켜 코드 중복을 제거하고, 가독성 또한 증대 시킬 수 있습니다.

### Kotest Assertions

1. Match

```kotlin
// 기본형
name shouldBe "sam" // assertThat(name).isEqualTo("sam")
name shouldNotBe null // assertThat(name).isNull()

// 체인형 -> 여러 조건을 chaining 할 수 있습니다
myImageFile.shouldHaveExtension(".jpg").shouldStartWith("https").shouldBeLowerCase()
```

2. Inspectors(점검자)

- test code의 collection이 있다면 element에 대한 test를 진행

```kotlin
mylist.forExactly(3) {
    it.city shouldBe "Chicago"
} // my list중 정확히 3개의 element의 city가 Chicargo 이다

val xs = listOf("sam", "gareth", "timothy", "muhammad")

xs.forAtLeast(2) { // 최소 2개의 요소가 lamdba 식이 true여야한다
    it.shouldHaveMinLength(7) // length가 7 보다 이하일 경우 true 초과 일 경우 false
} 
```

3. Exceptions

- test 실행 결과 exception 발생

```kotlin
shouldThrow {
    assertThrows { }
    // code in here that you expect to throw an IllegalAccessException
}
```