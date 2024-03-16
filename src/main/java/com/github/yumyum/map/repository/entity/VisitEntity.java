package com.github.yumyum.map.repository.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private MemberEntity memberEntity;
    @ManyToOne
    @JoinColumn(name = "rstr_id")
    private RestaurantEntity restaurantEntity;
    @Column(name="visit_date")
    private Date visitDate;
    @Column(name = "rstr_name")
    private String rstrName;
    @Column(name = "address")
    private String address;
    @Column(name = "tell_number")
    private String tellNumber;

}
