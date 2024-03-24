package com.github.yumyum.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"chatroomId", "title"})
@Builder
@Entity
@Table(name = "chatroom")
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private int chatroomId;

    @Column
    private String title;

    @Column
    private byte[] profile;

    @JsonIgnore
    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @JsonIgnore
    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @JsonIgnore
    @OneToMany(mappedBy = "chatroom")
    private List<MemberChatroom> memberChatroom = new ArrayList<>();
}
