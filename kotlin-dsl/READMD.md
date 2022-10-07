# Kotin DSL

## 1. DSL과 Kotlin DSL 정의

### 정의

1 DSL

- 모든 문제를 범용적으로 풀어낼 수 있는 **범용프로그래밍 언어**와 특정 과업 또는 영역에 초점을 맞추고, 그 영역에 필요하지 않은 기능을 없앤 **영역 특화 언어**로 구분되었습니다
    - 범용 프로그래밍 언어 : C언어. Java ...etc
    - 영역 특화 언어: SQL, HTML ...etc

### 필요성

- API는 사물간의 접전 간의 상호작용하게 해주는 어떤 것을 모두 표현한 말입니다. 그리고 이 interface는 유지 보수가 코드의 품질을 결정 짓습니다
- 코드의 가독성과 유지 보수성을 증대 시키는 방법은, 불필요한 구문이 없이 간결하고, 이름과 개념이 명확해야 합니다. 그리고 자율적인 객체를 생성해야 합니다
- DSL은 특정 도메인에서 군더더기 없이 간단하게 선언적으로 API를 제공하기 떄문에 간결성과 가독성에 좋은 효과를 제공해줍니다

### 특징

1. DSL

- 영역 특화 언어는 범용 프로그래밍 언어와 달리 선언적인 프로그래밍 언어 입니다. 명령형은 연산이 완수하기 위해서 필요한 각 단계를 순서대로 정확하게 기술한 반면, 선언형은 원하는 결과만 기술, 세부사항은 숨깁니다
- DSL은 선언 적인 프로그래밍 언어 이므로 여러 범용 프고그래밍 언어와 같이 조합해서 사용하기에는 어려움이 존재합니다
- DSL을 사용하는 방법중 하나가 DSL문법을 String으로 표현해서 사용하는 방법이 있지만 컴파일 타임에러를 확인하기 어렵습니다. 그리고 DSL 문법또한 학습해야 하므로 러닝커브도 존재합니다
- DSL의 단점을 해결하기 위해서 범용 프로그래밍 언어는 **내부 DSL(아래 참고)**를 정의 해서 문제를 해결하였습니다
- DSL이라는 하나의 구조적인 문법이 존재해서, 문법을 강력하게 지켜야 한다.


2. 내부 DSL 정의

- 범용 언어로 작성된 프로그래밍 언어의 일부이며, 범용과 동일한 문법을 제공합니다
- 컴파일 타임에 문법적인 오류를 잡을 수 있습니다
  ex) JPA(SQL - java/kotlin) ... etc

3. Kotlin DSL

- Kotlin 에서 제공해고 있는 내부 DSL 입니다
- kotlin 에서는 확장함수, 중위함수, 람다 등을 통해서 간결한 코드를 작성할 수 있었습니다.
- Kotiln DSL은 위 간결한 코드를 활용해 더 간결하고 가독성을 증진 시키기 위해서 사용 되어 집니다
- kotlin에서는 DSL을 통해서 람다, 확장 함수 등 ... 과 같이 간결한 구문을 제공하는 기능에 의존하고 있으며, 이런 구문들을 여러개 호출해 조합해서 만드는 기능에 의존해고 있습니다.
- DSL은 연산자 오버로딩 중위 호출과 같은 기능에 의존하기도 합니다
- Kotlin DSL은 람다를 중첩시키거나, 메서드 호출을 연쇄시키는 방식으로 구조를 만듭니다

## 2. 수신객체 람다와 Kotlin DSL

### 수신객체 람다 정의

- 람다 함수를 사용할 때 수신하는 객체(it/변수명)을 명시하지 않고, 람다 본문 안에서 다른 객체의 method를 호출할 수 있게 하는 것을 의미 합니다
- 대표적으로 apply, with 이 있습니다

### Kotlin DSL에서의 수신객체 람다 적용

```kotlin
fun buildString(builderAction: (StringBuilder) -> Unit): String {
    val sb = StringBuilder()
    builderAction(sb)
    return sb.toString()
}

fun main() {
    val s = buildString {
        it.append("Hello, ") // it == StringBuilder Obj 
        it.append("World!")
    }
    print(s) // "Hellow, World!"
}
```

- 위코드에서 `builderAction` 는 람다를 받는다. 간결한 코드를 작성하기에는 좋지만 builderString method를 호출할때 `it` 이나 특정 변수명을 정의를 해야하는 이슈가 존재한다
- `it`과 같은 특정 변수 명을 선언하지 않고 바로 람다에 받은 인자의 함수를 호출하는 방법은 builderAction 를 수신객체 지정 람다로 변경하는 것 입니다

```kotlin
fun buildString(builderAction: StringBuilder.() -> Unit): String {
    val sb = StringBuilder()
    builderAction(sb)
    return sb.toString()
}

fun main() {
    val s = buildString {
        append("Hello, ") // it == StringBuilder Obj 
        append("World!")
    }
    print(s) // "Hellow, World!"
}
```

- 위 변경된 코드에서 함수의 인자를 `builderAction: StringBuilder.() -> Unit` 로 변경하였습니다.
- 결론적으로 StringBuilder 내부에 존재하는 method를 it 변수 선언 없이 바로 사용할 수 있는 수신 객체 람다로 정의 하였습니다
- 그리고 `StringBuilder.()`를 `확장 함수 타입`을 사용했다 라고 표현하고 있습니다.
- 확장 함수의 본문에서는 확장 대상 클래스에 정의된 메소드를 마치 그 클래스 내부에서 호출하듯이 사용할 수 있는 장점이 존재합니다

```kotlin
@kotlin.internal.InlineOnly
public inline fun <T> T.apply(block: T.() -> Unit): T {
}
```

- 위 code는 apply 함수 입니다. 그리고 T type의 확장 함수를 인자로 받는 수신객체 람다 입니다
- 결론적으로 apply 함수는 setter/getter 나 T type의 모든 method 를 호출 할 수 있습니다

### Kotlin Builder

```kotlin

data class Group(
    val name: String,
    val groupRole: Role,
    val members: MutableList<Member>
) {
    fun addMember(newMember: Member) {
        members.add(newMember)
    }
}

data class Member(
    val name: String,
    val age: Int
)

data class Role(
    val name: String
)

fun main() {
    // 기존 객체 생성
    val companyGroup = Group(
        name = "회사 이름",
        groupRole = Role(
            name = "회사"
        ),
        members = ArrayList(),
    )

    companyGroup.addMember(
        Member(
            name = "member1",
            age = 20
        )
    )
    companyGroup.addMember(
        Member(
            name = "member2",
            age = 21
        )
    )
}
```

- Kotlin DSL은 내부 DSL이기 때문에 여러 함수를 호출하는 하나의 추상화된 함수를 선언할 수 있습니다
- Kotlin 에선 생성자 또한 apply나 `변수명 = 값` 형태로 객체를 생성할 수 있습니다
- 하지만 기본 생성자에 `addXXX` 와 같은 연산 로직이 들어갈 경우 기본 생성자가 아닌 부 생성자를 이용할 수 밖에 없습니다
- 이런 경우 Builder를 이용하면 더 직관적이고 간결한 코드를 작성할 수 있습니다
- 대표적으로 kotlin 의 builder 주로 Test code에서 임시 객체를 생성할떄 간결하게 객체를 생성하기 위해서 사용합니다

```kotlin

fun group(customize: GroupBuilder.() -> Unit) = GroupBuilder().apply(customize).build()
fun role(customize: RoleBuilder.() -> Unit) = RoleBuilder().apply(customize).build()
fun member(customizer: MemberBuilder.() -> Unit) = MemberBuilder().apply(customizer).build()

data class GroupBuilder(
    var name: String = "",
    var groupRole: Role = RoleBuilder().build(),
    var members: List<Member> = listOf(MemberBuilder().build())
) {
    fun build(): Group {
        val members = ArrayList<Member>()
        for (eachMember in this.members) {
            memberValidation(eachMember)
            members.add(eachMember)
        }

        return Group(
            name = name,
            groupRole = groupRole,
            members = members
        )
    }

    private fun memberValidation(member: Member) {
        if (member.age < 20) throw IllegalArgumentException("error")
    }
}

data class RoleBuilder(
    var name: String = ""
) {
    fun build() = Role(name = name)
}

data class MemberBuilder(
    var name: String = "",
    var age: Int = 0,
) {
    fun build() = Member(
        name = name,
        age = age
    )
}

fun main() {
    // 기존 객체 생성
    val companyGroup = Group(
        name = "회사 이름",
        groupRole = Role(
            name = "회사"
        ),
        members = ArrayList(),
    )

    companyGroup.addMember(
        Member(
            name = "member1",
            age = 20
        )
    )
    companyGroup.addMember(
        Member(
            name = "member2",
            age = 21
        )
    )

    print(companyGroup)

    // builder pattern
    val builderCompanyGroup: Group = group {
        name = "회사 이름"
        groupRole = role { name = "회사" }
        members = listOf(
            member { }
        )
    }

    print(builderCompanyGroup)
}
```

- 위 코드를 builder 패턴을 적용한 결과 입니다
- 간단한 예제 객체를 생성할 떄 기본 값을 임시로 넣을 수 있어서 좀더 간결하게 객체를 생성할 수 있습니다
- 그래도 특별한 상황이 아닐 경우에는 빌더 패턴을 적용하지 않고 간단한 코드를 작성할 수 있고, 명확하고, 간결한 기본 생성자를 응요하는 것이 더 적절할 수 있다

### invoke 관례 적용

1. 정의

- 객체를 함수처럼 호출 할 수 있는 관례 입니다
- operator 변경자가 붙은 invoke method를 정의가 들어있는 class으 ㅣ객체를 함수처럼 호출 할 수 있습니다

2. invoke 관례

```kotlin
class Greeter(val greeting: String) {
    operator fun invoke(name: String) {
        println("$greeting, $name")
    }
    operator fun invoke(name: String, nickName: String) = "$greeting $name $nickName"
}

fun main() {
    val greeting = Greeter("hello")
    greeting("kkh") // "hello, kkh" 출력

    print(greeting("kkh", "kkh_nickname"))
}
```

- 위 코드와 같이 Greeter 객체를 마치 함수 처럼 호출할 수 있습니다
- invoke는 시그니처에 대한 제약 사항이 없기 때문에 args를 원하는 만큼 넣을 수 있고, 반환 타입고 편한데로 정의라 할 수 있습니다
- inline 함수를 제외한 모든 람다는 함수형 interface를 구현하는 클래스로 컴파일 됩니다. 그리고 각 함수형 인터페이스 안에는 그 인터페이스 이름이 가리키는 개수만큼 파라미터를 받는 invoke 메소드가 있습니다

```kotlin
interface Function2<in P1, in P2, out R> {
    operator fun invoke(p1: P1, p2: P2): R
}
```

- 람다를 함수처럼 호출하면 이 관례에 따라 invoke 메서드가 호출되는 것으로 변환이 됩니다
- 위와 같은 방식을 이용하면 복잡한 람다 내부 로직을 여러 method로 분리하고 invoke를 통해서 통합할 수 있습니다

```kotlin

data class Issue(
    val id: String,
    val project: String,
    val type: String,
    val priority: String
)

class ImportantIssuePredicate(private val project: String) : (Issue) -> Boolean { // Function1<P1, R> class 상속
    override fun invoke(issue: Issue): Boolean { // 복잡한 filter logic을 한곳에 합치는 곳
        return issue.project == project && issue.isImportant()
    }

    private fun Issue.isImportant(): Boolean { // 복잡한 filter 로직 중 일부
        return type == "Bug" && (priority == "Major" || priority == "Critical")
    }
}

fun main() {
    val issues = listOf(
        Issue(
            id = "id1",
            project = "project 1",
            type = "Bug",
            priority = "Critical"
        ),
        Issue(
            id = "id2",
            project = "project 2",
            type = "Normal",
            priority = "Major"
        ),
    )

    val predicate = ImportantIssuePredicate("project 1") // filter 람다 객체 생성

    val filteredIssues = issues.filter(predicate)
    print(filteredIssues)
}
```

- 위 로직을 보면 복잡한 filter logic을 `ImportantIssuePredicate` 객체에 여러 method에 나눠서 정의를 했습니다
- `ImportantIssuePredicate` 람다 객체를 상속 받기 때문에 객체 생성후 사용할 때 invoke method가 호출됩니다. 결론적으로 필수적으로 invoke method 재정의 해야합니다.
- main 문에서 filter 는 함수 참조를 사용합니다 결론적으로 invoke method를 재정의하고 Boolean type을 반환하는 람다 객체를 재정의 한 `ImportantIssuePredicate` 입력해줄 수 있습니다.
