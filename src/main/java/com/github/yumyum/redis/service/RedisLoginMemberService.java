package com.github.yumyum.redis.service;


import lombok.RequiredArgsConstructor;
import com.github.yumyum.common.util.ObjectUtil;
import com.github.yumyum.redis.entity.RedisLoginMember;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisLoginMemberService {

    private final RedisTemplate<String, String> redisTemplate;

    public void setData(RedisLoginMember redisLoginMember, Long expiredTime) {
        String valueString = ObjectUtil.asString(redisLoginMember);
        redisTemplate.opsForValue().set(redisLoginMember.getKey(), valueString, expiredTime, TimeUnit.MILLISECONDS);
    }

    public Optional<RedisLoginMember> getData(RedisLoginMember redisLoginMember) {
        String valueString = redisTemplate.opsForValue().get(redisLoginMember.getKey());

        if (StringUtils.isEmptyOrWhitespace(valueString)) {
            return Optional.empty();
        }

        return Optional.ofNullable(ObjectUtil.readValue(valueString, RedisLoginMember.class));
    }

//    public RedisLoginMember getData(RedisLoginMember redisLoginMember) {
//        String valueString = redisTemplate.opsForValue().get(redisLoginMember.getKey());
//
//        if (StringUtils.isEmptyOrWhitespace(valueString)) {
//            return null;
//        }
//
//        return ObjectUtil.readValue(valueString, RedisLoginMember.class);
//    }

    public Boolean deleteData(RedisLoginMember redisLoginMember) {
        return redisTemplate.delete(redisLoginMember.getKey());
    }

}
