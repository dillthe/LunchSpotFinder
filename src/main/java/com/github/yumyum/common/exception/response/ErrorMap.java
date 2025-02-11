package com.github.yumyum.common.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMap {

    private String name;

    private Object value;

    private String message;

}
