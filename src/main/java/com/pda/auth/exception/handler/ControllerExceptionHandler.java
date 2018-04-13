package com.pda.auth.exception.handler;

import com.pda.auth.exception.*;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({AuthServiceException.class})
    public ResponseEntity handleServiceException(AuthServiceException exception, HttpServletRequest request) {
        CommonErrorDetail commonErrorDetail = prepareBuilder(exception, request)
                .status(exception.getStatus())
                .build();
        return new ResponseEntity<>(commonErrorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class, DataAccessException.class})
    public ResponseEntity handleServiceException(Exception exception, HttpServletRequest request) {
        CommonErrorDetail commonErrorDetail = prepareBuilder(exception, request)
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Data handling error")
                .build();
        return new ResponseEntity<>(commonErrorDetail, HttpStatus.BAD_REQUEST);
    }

    private CommonErrorDetail.CommonErrorDetailBuilder prepareBuilder(Exception exception, HttpServletRequest request) {
        return CommonErrorDetail.builder()
                .timestamp(new Date().getTime())
                .detail(exception.getMessage())
                .exception(exception.getClass().getName())
                .method(request.getMethod())
                .requestedPath(request.getServletPath());
    }
}