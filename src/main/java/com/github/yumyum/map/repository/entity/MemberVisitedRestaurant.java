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
@Table(name="member_visited_restaurant")
public class MemberVisitedRestaurant {
    @Id @Column(name="visit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer visitId;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurantEntity;
    @Column(name="visit_date")
    private Date visitDate;

}
