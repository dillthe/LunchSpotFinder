package menu.yumyum.yumyum.member.web;

import lombok.RequiredArgsConstructor;
import menu.yumyum.yumyum.member.dto.MemberReqDto;
import menu.yumyum.yumyum.member.dto.MemberResDto;
import menu.yumyum.yumyum.member.service.MemberService;
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
