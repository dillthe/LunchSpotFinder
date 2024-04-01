package com.github.yumyum.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer id;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "phone_num")
    private String phoneNum;


    @OneToMany(mappedBy = "member1")
    @JsonIgnore
    private List<Friendship> friendships1;

    @OneToMany(mappedBy = "member2")
    @JsonIgnore
    private List<Friendship> friendships2 = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<MemberChatroom> memberChatrooms = new ArrayList<>();

}
