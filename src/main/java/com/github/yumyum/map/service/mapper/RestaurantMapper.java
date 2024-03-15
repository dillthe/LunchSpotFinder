package com.github.yumyum.map.service.mapper;

//import com.github.yumyum.map.repository.entity.RestaurantEntity;
//import com.github.yumyum.map.web.dto.restaurant.RestaurantBody;
//import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import com.github.yumyum.map.repository.entity.RestaurantEntity;
import com.github.yumyum.map.repository.entity.RstrEntity;
import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {

   RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

   RestaurantEntity restaurantDTOtoRestaurantEntity(RestaurantDTO restaurantDTO);
   RstrEntity restaurantDTOtoRstrEntity(RestaurantDTO restaurantDTO);

//   RestaurantDTO restaurantEntityToRestaurantDTO(RestaurantEntity restaurantEntity);


}
