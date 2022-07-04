package com.example.jooq.app.application;

import com.example.jooq.app.adapter.persistence.entity.MemberRepository;
import com.example.jooq.app.application.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberDto> findMembers() {
        return memberRepository.findAll().stream()
                .map(MemberDto::from)
                .collect(Collectors.toList());
    }
}
