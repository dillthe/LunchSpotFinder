package com.github.yumyum.map.web.dto.restaurant;
import io.jsonwebtoken.Header;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestaurantResponse {
    private Header header;
    private List<RestaurantDTO> body;
}
