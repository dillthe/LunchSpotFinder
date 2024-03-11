package com.github.yumyum.map.service;

import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Response {
    private Header header;
    private List<RestaurantDTO> body;

    // getters and setters
}

