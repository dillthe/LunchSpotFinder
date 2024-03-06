package com.github.yumyum.map.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="member_interested_restaurant")
public class MemberInterestedRestaurant{
    @Id @Column(name="member_interested_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberInetestedId;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurantEntity;


}
