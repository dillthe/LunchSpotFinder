package com.github.yumyum.map.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.geo.Point;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private Point locationPoint;

//    @OneToMany(mappedBy = "memberEntity")
//    private List<MemberVisitedRestaurant> memberVisitedRestaurants;
//
//    @OneToMany(mappedBy = "memberEntity")
//    private List<MemberInterestedRestaurant> memberInterestedRestaurants;

}