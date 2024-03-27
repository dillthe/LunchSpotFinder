package com.github.yumyum.chat.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatMessage {

    private Integer roomId;
    private String content;
    private String sender;
    private MessageType type;
}
