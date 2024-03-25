package menu.yumyum.yumyum.common.exception.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import menu.yumyum.yumyum.common.util.ObjectUtil;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

@Slf4j
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<ErrorMap> errorMapList;

    public void write(HttpServletResponse response, HttpStatus status) throws IOException {
        log.error("Handle exception > {}", this);

        response.setStatus(status.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String jsonString = ObjectUtil.asString(this);
        response.getWriter().write(jsonString);
    }

}
