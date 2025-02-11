package com.github.yumyum.map.web.dto.interested;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InterestDTO {
        private Integer interestId;
        private Integer memberId;
        private Integer rstrId;
        private String rstrName;
        private String address;
        private String tellNumber;
}
