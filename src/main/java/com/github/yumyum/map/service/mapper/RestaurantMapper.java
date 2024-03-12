package com.github.yumyum.map.service.mapper;

import com.github.yumyum.map.repository.entity.Restaurant;
import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {
   RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

   //@Mapping(target = "restaurantId", ignore = true)
   Restaurant toEntity(RestaurantDTO restaurantDTO);



}
