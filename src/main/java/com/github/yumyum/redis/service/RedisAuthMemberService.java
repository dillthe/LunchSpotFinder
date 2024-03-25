package menu.yumyum.yumyum.redis.service;

import lombok.RequiredArgsConstructor;
import menu.yumyum.yumyum.common.util.ObjectUtil;
import menu.yumyum.yumyum.member.entity.RedisAuthMember;
import menu.yumyum.yumyum.redis.entity.RedisJoinMember;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisAuthMemberService {

    private final long EXPIRE_TIME = 60 * 1000 * 5L;
    private final RedisTemplate<String, String> redisTemplate;

    public void setData(RedisAuthMember redisAuthMember) {


        String valueString = ObjectUtil.asString(redisAuthMember);
        redisTemplate.opsForValue().set(redisAuthMember.getKey(), valueString, EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }

    public Optional<Object> getData(RedisAuthMember redisAuthMember) {
        String valueString = redisTemplate.opsForValue().get(redisAuthMember.getKey());

        if (StringUtils.isEmptyOrWhitespace(valueString)) {
            return Optional.empty();
        }

        return Optional.ofNullable(ObjectUtil.readValue(valueString, RedisAuthMember.class));
    }

    public Optional<RedisJoinMember> getJoinData(RedisAuthMember redisAuthMember) {
        String valueString = redisTemplate.opsForValue().get(redisAuthMember.getKey());

        if (StringUtils.isEmptyOrWhitespace(valueString)) {
            return Optional.empty();
        }

        return Optional.ofNullable(ObjectUtil.readValue(valueString, RedisJoinMember.class));
    }

    public void deleteData(String key){
        redisTemplate.delete(key);

    }
}
