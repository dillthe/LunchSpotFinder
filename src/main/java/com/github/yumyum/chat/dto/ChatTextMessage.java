package com.github.yumyum.chat.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
public class ChatTextMessage{

    private Integer roomId;
    private Integer memberId;
    private String content;
    private MessageType type;
}
