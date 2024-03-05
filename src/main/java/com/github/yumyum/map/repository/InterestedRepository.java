package com.github.yumyum.map.repository;

import com.github.yumyum.map.repository.entity.MemberInterestedRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestedRepository extends JpaRepository<MemberInterestedRestaurant, Integer> {
}
