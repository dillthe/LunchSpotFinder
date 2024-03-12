package com.github.yumyum.chat.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "friendship")
public class Friendship {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id")
    private Integer friendshipId;
    @ManyToOne(fetch = FetchType.LAZY)  // TODO CASCADE 설정
    @JoinColumn(name = "member_id1")
    private Member member1;
    @ManyToOne(fetch = FetchType.LAZY)  // TODO CASCADE 설정
    @JoinColumn(name = "member_id2")
    private Member member2;

}
