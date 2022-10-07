package com.example.kotindsl.dsl

class Greeter(val greeting: String) {
    operator fun invoke(name: String) {
        println("$greeting, $name")
    }

    operator fun invoke(name: String, nickName: String) = "$greeting $name $nickName"
}

data class Issue(
    val id: String,
    val project: String,
    val type: String,
    val priority: String
)

class ImportantIssuePredicate(private val project: String) : (Issue) -> Boolean { // Function1<P1, R> class
    override fun invoke(issue: Issue): Boolean {
        return issue.project == project && issue.isImportant()
    }

    private fun Issue.isImportant(): Boolean {
        return type == "Bug" && (priority == "Major" || priority == "Critical")
    }
}

fun main() {
    val greeting = Greeter("hello")
    greeting("kkh") // "hello, kkh" 출력

    println(greeting("kkh", "kkh_nickname"))

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

    val predicate = ImportantIssuePredicate("project 1")

    val filteredIssues = issues.filter(predicate)
    print(filteredIssues)
}
