package menu.yumyum.yumyum.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MemberReqDto {

    @JsonIgnore
    private Long memberId;
}
