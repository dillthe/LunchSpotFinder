package com.github.yumyum.map.web.dto.restaurant;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public class RestaurantDTO {
        private Integer restaurantId;
        private Integer rstrId;
        private String rstrNm;
        private String rstrRdnmadr;
        private String rstrLnnoAdres;
        private Double rstrLa;
        private Double rstrLo;
        private String rstrTelno;
        private String bsnsStatmBzcndNm;
        private String bsnsLcncNm;
        private String rstrIntrcnCont;
        private String rstrAreaClsfNm;
    }
