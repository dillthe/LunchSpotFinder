package menu.yumyum.yumyum.redis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import menu.yumyum.yumyum.common.constant.RedisKeyType;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RedisLoginMember {

    private Long memberId;
    private String refreshToken;

    @JsonIgnore
    public String getKey() {
        return RedisKeyType.LOGIN.name() +  ":" + memberId;
    }

}
