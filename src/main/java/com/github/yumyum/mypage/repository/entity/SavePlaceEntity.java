package com.github.yumyum.mypage.repository.entity;

import com.github.yumyum.map.repository.entity.RestaurantEntity;
import com.github.yumyum.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "save_place")
public class SavePlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "save_sn")
    private Integer saveSn;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "rstr_id")
    private RestaurantEntity restaurant;

    @CreationTimestamp
    @Column(name = "save_dt")
    private Date saveDt;

}
