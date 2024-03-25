package menu.yumyum.yumyum.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import menu.yumyum.yumyum.auth.dto.*;
import menu.yumyum.yumyum.common.exception.*;
import menu.yumyum.yumyum.common.exception.response.ErrorMap;
import menu.yumyum.yumyum.common.exception.response.ErrorMessage;
import menu.yumyum.yumyum.common.security.JwtProvider;
import menu.yumyum.yumyum.member.entity.Member;
import menu.yumyum.yumyum.member.entity.RedisAuthMember;
import menu.yumyum.yumyum.member.repository.MemberRepository;
import menu.yumyum.yumyum.redis.entity.RedisJoinMember;
import menu.yumyum.yumyum.redis.entity.RedisLoginMember;
import menu.yumyum.yumyum.redis.service.RedisAuthMemberService;
import menu.yumyum.yumyum.redis.service.RedisLoginMemberService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;


@RequiredArgsConstructor
@Service
public class AuthService {

    private final JavaMailSender mailSender;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final RedisLoginMemberService redisLoginMemberService;

    private final RedisAuthMemberService redisAuthMemberService;

    private final MemberRepository memberRepository;

    public LoginResDto login(LoginReqDto dto) {

        String decLoginId = dto.getLoginId();
        String decPassword = dto.getPassword();

        Member member = memberRepository.findByLoginId(decLoginId)
                .orElseThrow(() -> BadCredentialException.fire(
                        ErrorMap.builder()
                                .message(ErrorMessage.INVALID_ACCOUNT)
                                .name("loginId")
                                .value(decLoginId)
                                .build()));

        boolean isEqualPassword = passwordEncoder.matches(decPassword, member.getPassword());

        if (!isEqualPassword) {
            throw BadCredentialException.fire(
                    ErrorMap.builder()
                            .message(ErrorMessage.INVALID_ACCOUNT)
                            .name("loginId")
                            .value(member.getLoginId())
                            .build());
        }


        JwtProvider.Token accessToken = jwtProvider.createAccessToken(member);
        JwtProvider.Token refreshToken = jwtProvider.createRefreshToken(member);

        RedisLoginMember redisLoginMember = RedisLoginMember.builder()
                .memberId(member.getId())
                .refreshToken(refreshToken.getValue())
                .build();

        redisLoginMemberService.setData(redisLoginMember, refreshToken.getExpiredTime());

        return LoginResDto.builder()
                .loginId(member.getLoginId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void join(SignUpReqDto dto) {

        /* 아이디 중복체크 */
        Optional<Member> optionalMember = memberRepository.findByLoginId(dto.getLoginId());

        boolean isPresent = optionalMember.isPresent();
        if (isPresent) {
            /* 아이디 중복 Exception */
            throw DuplicatedResourceException.fire(ErrorMap.builder()
                    .message("이미 존재하는 이메일입니다.")
                    .name("loginId")
                    .value(dto.getLoginId())
                    .build());
        }

        /* 메일 인증번호 전송 */
        String authCode = createAuthCode();
        boolean isSend = sendEmail(authCode, dto.getLoginId());

        if (!isSend) {
            /* 이메일 발송 실패 Exception */
            throw EmailSendFailureException.fire();
        }

        /* 레디스 저장 */
        RedisJoinMember redisJoinMember = RedisJoinMember.builder()
                .isAuth(false)
                .authCode(authCode)
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .memberName(dto.getMemberName())
                .phoneNum(dto.getPhoneNum())
                .build();

        redisAuthMemberService.setData(redisJoinMember);
    }

    public void joinConfirm(SignUpConfirmReqDto dto) {

        RedisJoinMember redisJoinMember = RedisJoinMember.builder()
                .loginId(dto.getLoginId())
                .build();

        redisJoinMember = redisAuthMemberService.getJoinData(redisJoinMember)
                .orElseThrow(InvalidParameterException::fire);

        if (!redisJoinMember.isAuth()) {
            throw NotAuthenticatedException.fire();
        }

        /* RedisJoinMemeber -> member */
        Member member = Member.builder()
                .loginId(redisJoinMember.getLoginId())
                .password(passwordEncoder.encode(redisJoinMember.getPassword()))
                .memberName(redisJoinMember.getMemberName())
                .phoneNum(redisJoinMember.getPhoneNum())
                .build();
        memberRepository.save(member);
    }

    public void checkAuthCode(CheckAuthCodeReqDto dto) {

        RedisAuthMember redisAuthMember;

        RedisJoinMember redisJoinMember = RedisJoinMember.builder()
                .authCode(dto.getAuthCode())
                .loginId(dto.getLoginId())
                .build();

        redisAuthMember = redisAuthMemberService.getJoinData(redisJoinMember)
                .orElseThrow(InvalidParameterException::fire);

        String userCode = dto.getAuthCode();
        if (redisAuthMember == null || redisAuthMember.getAuthCode() == null || !redisAuthMember.getAuthCode().equals(userCode)) {
            throw InvalidParameterException.fire(ErrorMap.builder()
                    .message(ErrorMessage.INVALID_AUTH_NUM)
                    .name("authCode")
                    .value(userCode)
                    .build());
        }

        redisAuthMember.setAuth(true);
        redisAuthMemberService.setData(redisAuthMember);
    }

    public void getLoginStatus(LoginStatusReqDto dto) {
        Member member = memberRepository.findByLoginId(dto.getLoginId())
                .orElseThrow(() -> BadCredentialException.fire(
                        ErrorMap.builder()
                                .message(ErrorMessage.INVALID_ACCOUNT)
                                .name("loginId")
                                .value(dto.getLoginId())
                                .build()));

        RedisLoginMember redisLoginMember = RedisLoginMember.builder()
                .memberId(member.getId())
                .build();

        Optional<RedisLoginMember> optionalRedisLoginMember= redisLoginMemberService.getData(redisLoginMember);

        boolean loggedIn = optionalRedisLoginMember.isPresent();

        if(loggedIn){
            LoginStatusResDto.builder()
                    .loggedIn(true)
                    .build();
        }else {
            throw InvalidParameterException.fire(ErrorMap.builder()
                    .message(ErrorMessage.NOT_FOUND_MEMBER)
                    .name("agent")
                    .build());
        }
    }

    public ReissueResDto reissue(ReissueReqDto dto) throws InvalidParameterException {

        if (dto.getRefreshToken() == null) {
            throw InvalidParameterException.fire(
                    ErrorMap.builder()
                            .message(ErrorMessage.INVALID_REFRESH_TOKEN)
                            .name("refreshToken")
                            .build());
        }

        boolean isExpiration = jwtProvider.isExpiration(dto.getRefreshToken(), new Date());

        if (isExpiration) {
            throw InvalidParameterException.fire(
                    ErrorMap.builder()
                            .message(ErrorMessage.INVALID_REFRESH_TOKEN)
                            .name("refreshToken")
                            .value(dto.getRefreshToken())
                            .build());
        }

        Authentication authentication = jwtProvider.getAuthentication(dto.getRefreshToken());
        Long memberId = Long.parseLong(authentication.getName());

        RedisLoginMember redisLoginMember = RedisLoginMember.builder()
                .memberId(memberId)
                .build();

        Optional<RedisLoginMember> optionalRedisLoginMember = redisLoginMemberService.getData(redisLoginMember);

        if (optionalRedisLoginMember.isEmpty() ||
                !dto.getRefreshToken().equals(optionalRedisLoginMember.get().getRefreshToken())) {

            throw InvalidParameterException.fire(
                    ErrorMap.builder()
                            .message(ErrorMessage.INVALID_REFRESH_TOKEN)
                            .name("refreshToken")
                            .value(dto.getRefreshToken())
                            .build());
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BadCredentialException.fire(
                        ErrorMap.builder()
                                .message(ErrorMessage.NOT_FOUND_MEMBER)
                                .name("memberId")
                                .value(memberId)
                                .build()));

        JwtProvider.Token accessToken = jwtProvider.createAccessToken(member);
        JwtProvider.Token refreshToken;

        /* 7일 이내에 refresh token이 만료될 경우에만 재생성 */
        Date sevenDaysLater = addDaysToDate(new Date(), 7);
        if (jwtProvider.isExpiration(dto.getRefreshToken(), sevenDaysLater)) {
            refreshToken = jwtProvider.createRefreshToken(member);

            redisLoginMember = RedisLoginMember.builder()
                    .memberId(memberId)
                    .refreshToken(refreshToken.getValue())
                    .build();

            redisLoginMemberService.setData(redisLoginMember, refreshToken.getExpiredTime());

        } else {
            refreshToken = jwtProvider.toToken(dto.getRefreshToken(), JwtProvider.TokenType.REFRESH);
        }

        return ReissueResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(LogoutReqDto dto) {
        RedisLoginMember redisLoginMember = RedisLoginMember.builder()
                .memberId(dto.getMemberId())
                .build();

        redisLoginMemberService.deleteData(redisLoginMember);
    }

    private String createAuthCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(3); //index에 0,1,2 랜덤으로 발생

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97));
                case 1 -> key.append((char) (random.nextInt(26) + 65));
                default -> key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    private boolean sendEmail(String authCode, String loginId) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(loginId);
            helper.setSubject("냠냠이 인증번호 입니다.");
            helper.setText("인증번호는 " + authCode + "입니다.");
            mailSender.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

    }



    private Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

}
