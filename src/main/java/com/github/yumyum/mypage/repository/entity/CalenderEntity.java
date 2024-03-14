package com.github.yumyum.mypage.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "calender")
public class CalenderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calender_sn")
    private int calenderSn;

    @Column(name = "calender_cn")
    private String calenderCn;

    @Column(name = "memo_dt")
    private String memoDt;

    @Column(name = "reg_dt")
    private String regDt;

    @Column(name = "user_sn")
    private int userSn;
}
