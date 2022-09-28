package com.example.jooq.app.application;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberServiceTest {
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    MemberService memberService;
//
//    private void saveMembmer(String name) {
//        memberRepository.save(new Member(name));
//    }
//
//    @Test
//    void findMembersTest() {
//        // given
//        saveMembmer("1");
//        saveMembmer("2");
//        saveMembmer("3");
//
//        // when
//        List<MemberDto> members = memberService.findMembers();
//
//        // then
//        assertThat(members).extracting("name")
//                .containsExactlyInAnyOrderElementsOf(List.of("1", "2", "3"));
//    }
}