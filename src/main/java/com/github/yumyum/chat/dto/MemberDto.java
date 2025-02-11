package com.github.yumyum.chat.dto;

import lombok.*;

@Getter @Setter
@ToString
@Builder
public class MemberDto {

    private Integer id;
    private String loginId;
    private String memberName;

    public MemberDto() {
    }

    public MemberDto(Integer id, String loginId, String memberName) {
        this.id = id;
        this.loginId = loginId;
        this.memberName = memberName;
    }
}
