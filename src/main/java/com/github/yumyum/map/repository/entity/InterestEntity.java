package com.github.yumyum.map.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    private MemberEntity memberEntity;
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
