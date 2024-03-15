package com.github.yumyum.map.web.dto.restaurant;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantDTO {
    private Long rstrId;
    private String rstrName;
    private String address;
    private String latitude;
    private BigDecimal longtitude;
    private String tell_number;
    private String cuisine;
    }
