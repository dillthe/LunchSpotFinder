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
        private Long rstrId;
        private String rstrNm;
        private String rstrRdnmadr;
        private String rstrLnnoAdres;
        private BigDecimal rstrLa;
        private BigDecimal rstrLo;
        private String rstrTelno;
        private String bsnsStatmBzcndNm;
        private String bsnsLcncNm;
        private String rstrIntrcnCont;
        private String rstrAreaClsfNm;
    }
