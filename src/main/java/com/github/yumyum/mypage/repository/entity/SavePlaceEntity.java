package com.github.yumyum.mypage.repository.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String placeSn;

    @Column(name = "save_dt")
    private String saveDt;


}
