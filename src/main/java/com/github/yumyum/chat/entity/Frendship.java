package com.github.yumyum.chat.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "frendship")
public class Frendship {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frendship_id")
    private Integer frendshipId;
    @ManyToOne(fetch = FetchType.LAZY)  // TODO CASCADE 설정
    @JoinColumn(name = "member_id1")
    private Member member1;
    @ManyToOne(fetch = FetchType.LAZY)  // TODO CASCADE 설정
    @JoinColumn(name = "member_id2")
    private Member member2;

}
