package com.example.kotindsl.dsl

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