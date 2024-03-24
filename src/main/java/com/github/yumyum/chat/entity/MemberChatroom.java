package com.github.yumyum.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "member_chatroom")
public class MemberChatroom {
    @Id
    @Column(name = "member_id")
    private Integer memberId;

    @Id
    @Column(name = "chatroom_id")
    private Integer chatroomId;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)  // TODO CASCADE 설정
//    @JoinColumn(name = "memberChatrooms")
//    private Member member;
//
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)  // TODO CASCADE 설정
//    @JoinColumn(name = "memberChatroom")
//    private Chatroom chatroom;

    @Builder
    public MemberChatroom(Integer memberId, Integer chatroomId) {
        this.memberId = memberId;
        this.chatroomId = chatroomId;
    }
}
