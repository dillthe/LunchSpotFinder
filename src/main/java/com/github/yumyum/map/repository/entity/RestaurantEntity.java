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
//@Builder
@Entity
@Table(name="restaurant")
public class RestaurantEntity {
    @Id @Column(name="restaurant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantId;

    @Column(name="name")
    private String name;

    @Column(name="address")
    private String address;

    @Column(name="location_point")
    private Point locationPoint;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="cuisine")
    private String cuisine;
//
//    @OneToMany(mappedBy = "RestaurantEntity")
//    private List<MemberVisitedRestaurant> memberVisitedRestaurants;
//
//    @OneToMany(mappedBy = "RestaurantEntity")
//    private List<MemberInterestedRestaurant> memberInterestedRestaurants;
//

}
