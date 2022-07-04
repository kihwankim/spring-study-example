package com.example.jooq.app.adapter.persistence.entity;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jooq.public_.tables.Member.MEMBER;

@Repository
@RequiredArgsConstructor
public class MemberJooqRepository {

    private final DSLContext dsl;

    public List<Member> findAll() {
        return dsl.selectFrom(MEMBER)
                .fetchInto(Member.class);
    }
}
