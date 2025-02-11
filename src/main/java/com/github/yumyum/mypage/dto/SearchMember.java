package com.github.yumyum.mypage.dto;

import com.github.yumyum.member.dto.MemberResDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchMember {
    private int id;
    private String searchId;

}
