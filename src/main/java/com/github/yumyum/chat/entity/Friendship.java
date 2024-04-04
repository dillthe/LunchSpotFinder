package com.github.yumyum.chat.entity;

import com.github.yumyum.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"friendshipId"})
@Builder
@Entity
@Table(name = "friendship")
public class Friendship {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id")
    private Integer friendshipId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id1")
    private Member member1;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id2")
    private Member member2;
}
