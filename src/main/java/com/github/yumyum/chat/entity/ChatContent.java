package com.github.yumyum.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.yumyum.chat.dto.GameType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString(of = {"chatContentId", "text", "createdDate", "updatedDate"})
@Entity
@Table(name = "chat_content")
public class ChatContent {

//    @JsonIgnore
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_content_id")
    private int chatContentId;

    @Column
    private String text;

    @Column
    private byte[] img;

    @Column(name = "game_type")
    private GameType gameType;

    @JsonIgnore
    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @JsonIgnore
    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)  // TODO CASCADE 설정
    @JoinColumns({
            @JoinColumn(name = "member_id"),
            @JoinColumn(name = "chatroom_id")
    })
    private MemberChatroom memberChatroom;

    @Builder
    public ChatContent(String text, MemberChatroom memberChatroom) {
        this.text = text;
        this.memberChatroom = memberChatroom;
    }

    @Builder
    public ChatContent(byte[] img, MemberChatroom memberChatroom) {
        this.img = img;
        this.memberChatroom = memberChatroom;
    }
}
