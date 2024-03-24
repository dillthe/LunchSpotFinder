package com.github.yumyum.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = {"memberId", "email", "name"})
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int memberId;

    @Column
    private String email;

    @Column
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "member1")
    private List<Friendship> friendships1;

    @JsonIgnore
    @OneToMany(mappedBy = "member2")
    private List<Friendship> friendships2 = new ArrayList<>();

//    @JsonIgnore
//    @OneToMany(mappedBy = "member")
//    private List<MemberChatroom> memberChatrooms = new ArrayList<>();

    public Member(int memberId) {
        this.memberId = memberId;
    }
}
