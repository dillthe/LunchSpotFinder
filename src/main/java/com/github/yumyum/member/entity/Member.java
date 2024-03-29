package com.github.yumyum.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.yumyum.chat.entity.Friendship;
import com.github.yumyum.chat.entity.MemberChatroom;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer id;

    private String loginId;
    private String password;
    private String memberName;
    private String phoneNum;


    @JsonIgnore
    @OneToMany(mappedBy = "member1")
    private List<Friendship> friendships1;

    @JsonIgnore
    @OneToMany(mappedBy = "member2")
    private List<Friendship> friendships2 = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<MemberChatroom> memberChatrooms = new ArrayList<>();

}
