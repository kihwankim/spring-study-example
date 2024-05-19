package com.example.jdslexample.common.init

import com.example.jdslexample.persistence.entity.MemberEntity
import com.example.jdslexample.persistence.entity.RoleEntity
import com.example.jdslexample.persistence.repository.MemberRepository
import com.example.jdslexample.persistence.repository.RoleRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InitProcessor(
    private val testMemberInit: TestMemberInit
) {
    //    @PostConstruct
    fun init() {
        testMemberInit.initAll()
    }
}

@Component
@Transactional
class TestMemberInit(
    private val memberRepository: MemberRepository,
    private val roleRepository: RoleRepository,
) {
    lateinit var roleUser: RoleEntity
    lateinit var roleAdmin: RoleEntity

    @Transactional
    fun initAll() {
        roleInit()
        memberInit()
    }

    fun roleInit() {
        roleUser = RoleEntity(name = "user")
        roleAdmin = RoleEntity(name = "admin")
        roleRepository.save(roleUser)
        roleRepository.save(roleAdmin)
    }

    fun memberInit() {
        val memberEntity = MemberEntity(name = "test1")
        memberEntity.addRole(roleUser)
        memberRepository.save(memberEntity)

        val adminMember = MemberEntity(name = "test2")
        adminMember.addRole(roleAdmin)
        memberRepository.save(adminMember)

        val allMember = MemberEntity(name = "test3")
        allMember.addRole(roleAdmin)
        allMember.addRole(roleAdmin)
        memberRepository.save(allMember)

    }
}