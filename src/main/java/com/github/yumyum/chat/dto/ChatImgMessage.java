package com.github.yumyum.chat.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
public class ChatImgMessage{

    private Integer roomId;
    private Integer memberId;
    private MultipartFile file;
    private MessageType type;
}
