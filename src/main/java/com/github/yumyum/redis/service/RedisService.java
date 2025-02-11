package com.github.yumyum.redis.service;


import lombok.RequiredArgsConstructor;
import com.github.yumyum.redis.dto.RedisInviteReqDto;
import com.github.yumyum.redis.entity.RedisInviteMember;
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
