package com.github.yumyum.chat.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
public class ChatMessage {

    private Integer roomId;
    private Integer memberId;
    private String content;
    private MessageType type;
}
