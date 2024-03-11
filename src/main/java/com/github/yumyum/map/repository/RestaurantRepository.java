package com.github.yumyum.map.repository;

import com.github.yumyum.map.repository.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
//public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {
//}

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>
{
}
