package com.github.yumyum.mypage.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_friend")

public class UserMatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_sn")
    private int matchSn;

    @Column(name = "match_code")
    private char matchCode;

    @CreationTimestamp
    @Column(name = "send_dt")
    private String sendDt;

    @UpdateTimestamp
    @Column(name = "match_dt")
    private String matchDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user", referencedColumnName = "user_sn")
    private UserEntity sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_user", referencedColumnName = "user_sn")
    private UserEntity receiveUser;


}
