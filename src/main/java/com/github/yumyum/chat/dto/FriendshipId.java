package com.github.yumyum.chat.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
public class FriendshipId {

    private Integer friendshipId;
}
