package com.github.yumyum.chat.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
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

    public Member(int memberId) {
        this.memberId = memberId;
    }

    public Member(int memberId, String email, String name) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
    }
}
