package com.github.yumyum.map.repository.entity;

import com.github.yumyum.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Interested_Restaurant",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "rstr_id"})})
public class InterestEntity{
    @Id @Column(name="interest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer interestId;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "rstr_id")
    private RestaurantEntity restaurantEntity;

    @Column(name = "rstr_name")
    private String rstrName;

    @Column(name = "address")
    private String address;

    @Column(name = "tell_number")
    private String tellNumber;

}
