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
@Table(name = "calendar")
public class CalendarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_sn")
    private int calendarSn;

    @Column(name = "calendar_cn")
    private String calendarCn;

    @Column(name = "memo_dt")
    private Date memoDt;

    @CreationTimestamp
    @Column(name = "reg_dt")
    private Date regDt;

    @Column(name = "user_sn")
    private int userSn;
}
