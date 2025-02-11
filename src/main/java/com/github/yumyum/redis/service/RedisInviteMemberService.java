package com.github.yumyum.redis.service;

import lombok.RequiredArgsConstructor;
import com.github.yumyum.common.util.ObjectUtil;
import com.github.yumyum.redis.entity.RedisInviteMember;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisInviteMemberService {

    private final RedisTemplate<String, String> redisTemplate;

    public void setData(RedisInviteMember redisInviteMember) {
        long expiredTime = 1000* 60 * 3L;
        String valueString = ObjectUtil.asString(redisInviteMember);
        redisTemplate.opsForValue().set(redisInviteMember.getKey(), valueString, expiredTime, TimeUnit.MILLISECONDS);
    }

    public Optional<RedisInviteMember> getData(RedisInviteMember redisInviteMember) {
        String valueString = redisTemplate.opsForValue().get(redisInviteMember.getKey());

        if (StringUtils.isEmptyOrWhitespace(valueString)) {
            return Optional.empty();
        }

        return Optional.ofNullable(ObjectUtil.readValue(valueString, RedisInviteMember.class));
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);

    }


}