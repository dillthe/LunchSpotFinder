package com.github.yumyum.map.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.geo.Point;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Member")
public class MemberEntity {
    @Id @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;
    @Column(name="email")
    private String email;
    @Column(name="name")
    private String name;
    @Column(name="password")
    private String password;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="location_point")
    private Point locationPoint;

//    @OneToMany(mappedBy = "memberEntity")
//    private List<MemberVisitedRestaurant> memberVisitedRestaurants;
//
//    @OneToMany(mappedBy = "memberEntity")
//    private List<MemberInterestedRestaurant> memberInterestedRestaurants;

//    @OneToMany(mappedBy = "memberInterestedRestaurant", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<RestaurantEntity> memberInterestedRestaurants = new HashSet<>();

}