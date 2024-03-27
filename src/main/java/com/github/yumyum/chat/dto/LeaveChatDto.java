package com.github.yumyum.chat.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LeaveChatDto {
    private Integer memberId;
    private Integer chatroomId;
}
