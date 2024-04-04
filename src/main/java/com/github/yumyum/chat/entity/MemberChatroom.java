package com.github.yumyum.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.yumyum.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "member_chatroom")
public class MemberChatroom {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)  // TODO CASCADE 설정
    @JoinColumn(name = "member_id") // 실제 DB column
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)  // TODO CASCADE 설정
    @JoinColumn(name = "chatroom_id") // 실제 DB column
    private Chatroom chatroom;
}
