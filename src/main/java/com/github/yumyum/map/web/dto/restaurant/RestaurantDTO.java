package com.github.yumyum.map.web.dto.restaurant;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantDTO {
        private Integer restaurantId;
        private String name;
        private String address;
        private Point locationPoint;
        private String phoneNumber;
        private String cuisine;
}
