package com.github.yumyum.mypage.repository.entity;

import com.github.yumyum.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
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
    private LocalDate memoDt;

    @CreationTimestamp
    @Column(name = "reg_dt")
    private LocalDate regDt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
