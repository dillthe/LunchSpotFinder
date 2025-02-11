package com.github.yumyum.mypage.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.yumyum.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_match")

public class UserMatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_sn")
    private int matchSn;

    @Column(name = "match_code")
    private char matchCode;

    @CreationTimestamp
    @Column(name = "send_dt", updatable = false)
    private LocalDate sendDt;

    @Column(name = "match_dt", insertable = false)
    @UpdateTimestamp
    private LocalDate matchDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user", referencedColumnName = "member_id")
    @JsonIgnore
    private Member sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_user", referencedColumnName = "member_id")
    @JsonIgnore
    private Member receiveUser;
}
