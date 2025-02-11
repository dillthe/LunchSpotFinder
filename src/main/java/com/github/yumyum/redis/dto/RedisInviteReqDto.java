package com.github.yumyum.redis.dto;

import lombok.Data;

@Data
public class RedisInviteReqDto {

    private String inviteCode;
    private String receiverEmail;

}
