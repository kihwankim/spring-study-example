package com.example.jooq.app.application.dto;


import com.example.jooq.tables.pojos.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String name;

    public static MemberDto from(Member member) {
        return new MemberDto(member.getMemberId(), member.getName());
    }
}
