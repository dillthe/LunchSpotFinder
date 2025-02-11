package com.github.yumyum.map.web.dto.visited;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VisitDTO {
        private Integer visitedId;
        private Integer memberId;
        private Integer rstrId;
        private LocalDate visitDate;

        private String rstrName;
        private String address;
        private String tellNumber;
}
