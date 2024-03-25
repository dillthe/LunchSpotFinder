package com.github.yumyum.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberResDto {

    private Long memberId;
    private String loginId;
    private String memberName;
}
