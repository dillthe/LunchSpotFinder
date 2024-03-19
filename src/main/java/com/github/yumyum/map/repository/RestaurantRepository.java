package com.github.yumyum.map.repository;


import com.github.yumyum.map.repository.entity.RestaurantEntity;
import org.springframework.data.geo.Distance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {

    @Query(value = "SELECT r.* FROM restaurant r WHERE 6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * cos(radians(r.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(r.latitude))) <= :radius", nativeQuery = true)
    List<RestaurantEntity> findRestaurantsWithinRadiusForMember(@Param("latitude") BigDecimal latitude, @Param("longitude") BigDecimal longitude, @Param("radius") BigDecimal radius);
}
