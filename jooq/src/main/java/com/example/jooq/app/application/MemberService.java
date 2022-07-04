package com.example.jooq.app.application;

import com.example.jooq.app.adapter.persistence.entity.Member;
import com.example.jooq.app.adapter.persistence.entity.MemberJooqRepository;
import com.example.jooq.app.application.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJooqRepository memberJooqRepository;

    @Transactional(readOnly = true)
    public List<MemberDto> findMembers() {
        List<Member> findedMembers = memberJooqRepository.findAll();

        return findedMembers.stream()
                .map(MemberDto::from)
                .collect(Collectors.toList());
    }
}
