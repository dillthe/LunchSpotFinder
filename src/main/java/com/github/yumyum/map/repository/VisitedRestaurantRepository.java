package com.github.yumyum.map.repository;

import com.github.yumyum.map.repository.entity.MemberVisitedRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitedRestaurantRepository extends JpaRepository<MemberVisitedRestaurant, Integer> {
}
