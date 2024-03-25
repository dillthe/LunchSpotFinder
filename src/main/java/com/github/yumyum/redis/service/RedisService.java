package menu.yumyum.yumyum.redis.service;


import lombok.RequiredArgsConstructor;
import menu.yumyum.yumyum.redis.dto.RedisInviteReqDto;
import menu.yumyum.yumyum.redis.entity.RedisInviteMember;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisInviteMemberService redisInviteMemberService;

    public void inviteData(RedisInviteReqDto dto){
        RedisInviteMember redisInviteMember = RedisInviteMember.builder()
                .inviteCode(dto.getInviteCode())
                .receiverEmail(dto.getReceiverEmail())
                .build();

        redisInviteMemberService.setData(redisInviteMember);
    }

}
