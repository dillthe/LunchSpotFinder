package com.github.yumyum.member.web;

import lombok.RequiredArgsConstructor;
import com.github.yumyum.member.dto.MemberReqDto;
import com.github.yumyum.member.dto.MemberResDto;
import com.github.yumyum.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResDto> memberList(@PathVariable Long memberId, MemberReqDto dto) {
        dto.setMemberId(memberId);
        return ResponseEntity.ok(memberService.memberList(dto));
    }

}
