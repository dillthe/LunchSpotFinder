package com.github.yumyum.mypage.dto;

import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import com.github.yumyum.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveReqDTO {
    private Integer id;
    private Integer rstrId;

}
