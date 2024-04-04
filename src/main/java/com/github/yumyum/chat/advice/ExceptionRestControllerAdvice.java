package com.github.yumyum.chat.advice;

import com.github.yumyum.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionRestControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidValueException.class)
    public String handleNotFoundException(InvalidValueException ive){
        log.error(ive.getMessage());
        return ive.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFileException.class)
    public String handleNotFoundException(InvalidFileException ife){
        log.error(ife.getMessage());
        return ife.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidMsgException.class)
    public String handleNotFoundException(InvalidMsgException ime){
        log.error(ime.getMessage());
        return ime.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParamException.class)
    public String handleNotFoundException(InvalidParamException ipe){
        log.error(ipe.getMessage());
        return ipe.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException nfe){
        log.error(nfe.getMessage());
        return nfe.getMessage();
    }

//    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
//    @ExceptionHandler(NotAcceptException.class)
//    public String handleNotAcceptException(NotAcceptException nae){
//        log.error("Client 요청이 모종의 이유로 거부됩니다. " + nae.getMessage());
//        return nae.getMessage();
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(InvalidValueException.class)
//    public String handleInvalidValueException(InvalidValueException ive){
//        log.error("Client 요청에 문제가 있어 다음처럼 출력합니다. " + ive.getMessage());
//        return ive.getMessage();
//    }
//
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(AccessDeniedException.class)
//    public String handleAccessDeniedException(AccessDeniedException ade){
//        log.error("Client 요청에 문제가 있어 다음처럼 출력합니다. " + ade.getMessage());
//        return ade.getMessage();
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(CAuthenticationEntryPointException.class)
//    public String handleAuthenticationException(CAuthenticationEntryPointException ae){
//        log.error("Client 요청에 문제가 있어 다음처럼 출력합니다. " + ae.getMessage());
//        return ae.getMessage();
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleSineupDtoExceptionHandler(MethodArgumentNotValidException me) {
//        log.error("회원가입 요청에 문제가 있어 다음처럼 출력합니다. " + me.getMessage());
//        Map<String, String> errors = new HashMap<>();
//        me.getBindingResult().getAllErrors().forEach((error)-> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }
}
