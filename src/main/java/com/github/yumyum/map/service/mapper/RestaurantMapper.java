package com.github.yumyum.map.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {

   RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

//   RestaurantEntity restaurantDTOtoRestaurantEntity(RestaurantDTO restaurantDTO);

//   RestaurantDTO restaurantEntityToRestaurantDTO(RestaurantEntity restaurantEntity);


}
