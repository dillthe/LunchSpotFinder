package menu.yumyum.yumyum.redis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import menu.yumyum.yumyum.common.constant.RedisKeyType;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RedisInviteMember {

    private String inviteCode;
    private String receiverEmail;

    @JsonIgnore
    public String getKey() {
        return RedisKeyType.INVITE.name() + " :" + inviteCode;
    }

}
