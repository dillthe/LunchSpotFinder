package com.github.yumyum.common.exception.handler;

import jakarta.servlet.http.HttpServletResponse;
import com.github.yumyum.common.exception.CustomException;
import com.github.yumyum.common.exception.response.ErrorCode;
import com.github.yumyum.common.exception.response.ErrorMap;
import com.github.yumyum.common.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * controller, service에서 발생하는 오류 처리
 */
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public void handleCustomException(HttpServletResponse response, CustomException customException) throws IOException {
        ErrorCode errorCode = customException.getErrorCode();
        HttpStatus status = errorCode.getHttpStatus();
        String code = errorCode.getCode();
        String message = errorCode.getMessage();
        List<ErrorMap> errorMapList = customException.getErrorMapList();

        ErrorResponse.builder()
                .code(code)
                .message(message)
                .errorMapList(errorMapList)
                .build()
                .write(response, status);
    }

    @ExceptionHandler(BindException.class)
    public void handleValidationError(HttpServletResponse response, BindException exception) throws IOException {
        BindingResult bindingResult = exception.getBindingResult();

        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        HttpStatus status = errorCode.getHttpStatus();
        String code = errorCode.getCode();
        String message = errorCode.getMessage();
        List<ErrorMap> errorMapList = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            ErrorMap errorMap = ErrorMap.builder()
                    .name(fieldError.getField())
                    .value(fieldError.getRejectedValue())
                    .message(fieldError.getDefaultMessage())
                    .build();
            errorMapList.add(errorMap);
        }

        ErrorResponse.builder()
                .code(code)
                .message(message)
                .errorMapList(errorMapList)
                .build()
                .write(response, status);
    }
}