package com.github.yumyum.member.service;


import lombok.RequiredArgsConstructor;
import com.github.yumyum.common.exception.ResourceNotFoundException;
import com.github.yumyum.common.exception.response.ErrorMap;
import com.github.yumyum.common.exception.response.ErrorMessage;
import com.github.yumyum.member.dto.MemberReqDto;
import com.github.yumyum.member.dto.MemberResDto;
import com.github.yumyum.member.entity.Member;
import com.github.yumyum.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResDto memberList(MemberReqDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> ResourceNotFoundException.fire(
                        ErrorMap.builder()
                                .name("memberId")
                                .value(dto.getMemberId())
                                .message(ErrorMessage.MEMBER_NOT_FOUND)
                                .build()));

        return MemberResDto.builder()
                .memberId(member.getId())
                .loginId(member.getLoginId())
                .memberName(member.getMemberName())
                .build();
    }
}
