package com.github.yumyum.mypage.repository.entity;

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
    private int saveSn;

    @Column(name = "user_sn")
    private int userSn;

    @Column(name = "place_sn")
    private int placeSn;

    @CreationTimestamp
    @Column(name = "save_dt")
    private Date saveDt;

}
