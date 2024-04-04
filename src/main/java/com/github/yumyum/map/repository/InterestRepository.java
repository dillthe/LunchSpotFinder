package com.github.yumyum.map.repository;

import com.github.yumyum.map.repository.entity.InterestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<InterestEntity, Integer> {
//  Optional<InterestEntity> findByMemberEntityAndRestaurantEntity(MemberEntity memberEntity, RestaurantEntity restaurantEntity);
//
//  void deleteByMemberIdAndRstrId(Integer memberId, Integer rstrId);
}