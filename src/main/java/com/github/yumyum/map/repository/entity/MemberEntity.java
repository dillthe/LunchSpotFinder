package com.github.yumyum.map.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.geo.Point;

import java.math.BigDecimal;
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
    @Column(name="latitude")
    private BigDecimal latitude;
    @Column(name = "longtitude")
    private BigDecimal longitude;

//    @OneToMany(mappedBy = "memberEntity")
//    private List<MemberVisitedRestaurant> memberVisitedRestaurants;
//
//    @OneToMany(mappedBy = "memberEntity")
//    private List<MemberInterestedRestaurant> memberInterestedRestaurants;

//    @OneToMany(mappedBy = "memberInterestedRestaurant", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<RestaurantEntity> memberInterestedRestaurants = new HashSet<>();

}