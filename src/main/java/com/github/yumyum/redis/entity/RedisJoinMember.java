package menu.yumyum.yumyum.redis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import menu.yumyum.yumyum.common.constant.RedisKeyType;
import menu.yumyum.yumyum.member.entity.RedisAuthMember;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class RedisJoinMember extends RedisAuthMember {

    private String loginId;
    private String password;
    private String memberName;
    private String phoneNum;

    @JsonIgnore
    public String getKey() {
      return RedisKeyType.JOIN_AUTH.name() + ":" + loginId;
    }

}
