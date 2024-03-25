package menu.yumyum.yumyum.member.service;


import lombok.RequiredArgsConstructor;
import menu.yumyum.yumyum.common.exception.ResourceNotFoundException;
import menu.yumyum.yumyum.common.exception.response.ErrorMap;
import menu.yumyum.yumyum.common.exception.response.ErrorMessage;
import menu.yumyum.yumyum.member.dto.MemberReqDto;
import menu.yumyum.yumyum.member.dto.MemberResDto;
import menu.yumyum.yumyum.member.entity.Member;
import menu.yumyum.yumyum.member.repository.MemberRepository;
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
