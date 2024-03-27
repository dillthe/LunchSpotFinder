package com.github.yumyum.chat.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatroomDto {

    private List<Integer> memberIds;
    private MultipartFile profile;
    private String title;
}
