package com.github.yumyum.map.repository.entity;

import com.github.yumyum.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="visited_restaurant")
public class VisitEntity {
    @Id @Column(name="visit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer visitId;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "rstr_id")
    private RestaurantEntity restaurantEntity;
    @Column(name="visit_date")
    private LocalDate visitDate;
    @Column(name = "rstr_name")
    private String rstrName;
    @Column(name = "address")
    private String address;
    @Column(name = "tell_number")
    private String tellNumber;

}
