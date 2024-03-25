package menu.yumyum.yumyum.common.security;

import lombok.RequiredArgsConstructor;
import menu.yumyum.yumyum.common.exception.BadCredentialException;
import menu.yumyum.yumyum.member.entity.Member;
import menu.yumyum.yumyum.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(BadCredentialException::fire);

        return new CustomUserDetails(member);
    }
}
