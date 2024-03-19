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
//    List<RestaurantEntity> findByLocationNear(Point memberPoint, Distance distance);

//    @Query(value = "SELECT id, name, latitude, longitude, " +
//            "6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(latitude))) AS distance " +
//            "FROM restaurant " +
//            "HAVING distance <= :radius",
//            nativeQuery = true)
//    List<Restaurant> findRestaurantsWithinRadius(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radius") double radius);
//@Query(value = "SELECT * FROM restaurant WHERE " +
//        "6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(latitude))) <= :radius",
//        nativeQuery = true)
//List<RestaurantEntity> findRestaurantsWithinRadius(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radius") double radius);

//    @Query(value = "SELECT r.* FROM restaurant r, member m WHERE 6371 * acos(cos(radians(:latitude)) * cos(radians(m.latitude)) * cos(radians(m.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(m.latitude))) <= :radius AND m.member_id = :member_id,
//            nativeQuery = true)
//    List<RestaurantEntity> findRestaurantsWithinRadiusForMember(@Param("member_id") String memberId, @Param("radius") double radius, @Param("latitude") double latitude, @Param("longitude") double longitude);


    @Query(value = "SELECT r.* FROM restaurant r WHERE 6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * cos(radians(r.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(r.latitude))) <= :radius", nativeQuery = true)
    List<RestaurantEntity> findRestaurantsWithinRadiusForMember(@Param("latitude") BigDecimal latitude, @Param("longitude") BigDecimal longitude, @Param("radius") BigDecimal radius);

//    @Query(value = "SELECT r.* FROM restaurant r, member m WHERE r.member_id = m.member_id AND " +
//            "6371 * acos(cos(radians(:latitude)) * cos(radians(m.latitude)) * cos(radians(m.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(m.latitude))) <= :radius AND m.member_id = :member_id",
//            nativeQuery = true)
//    List<RestaurantEntity> findRestaurantsWithinRadiusForMember(@Param("member_id") String memberId, @Param("radius") double radius, @Param("latitude") double latitude, @Param("longitude") double longitude);


//    @Query(value = "SELECT id, name, latitude, longitude, " +
//            "6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(latitude))) AS distance " +
//            "FROM restaurant " +
//            "HAVING distance <= :radius",
//            nativeQuery = true)
//    List<RestaurantEntity> findRestaurantsWithinRadiusForMember(String memberId, double radius);
}
