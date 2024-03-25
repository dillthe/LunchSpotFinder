package com.github.yumyum.map.web.dto.restaurant;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantDTO {
    private Integer rstrId;
    private String rstrName;
    private String address;
    private BigDecimal rsLatitude;
    private BigDecimal rsLongitude;
    private String tell_number;
    private String cuisine;
    }
