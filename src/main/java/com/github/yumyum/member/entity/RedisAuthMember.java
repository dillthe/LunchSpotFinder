package com.github.yumyum.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public abstract class RedisAuthMember {

    private boolean isAuth;

    private String authCode;

    public abstract String getKey();

}
