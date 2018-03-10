package com.eagleoj.web.controller.exception;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.ShiroException;
import com.eagleoj.web.entity.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @author Smith
 **/
@RestControllerAdvice
public class GlobalExceptionController {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handle(MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldError();
        return new ResponseEntity(HttpStatus.BAD_REQUEST.value(), error.getField()+": "+error.getDefaultMessage(), null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handle(ConstraintViolationException e) {
        String s = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()).get(0);
        return new ResponseEntity(HttpStatus.BAD_REQUEST.value(), s, null);
    }

    @ExceptionHandler(ShiroException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handleShiro(HttpServletRequest request, Throwable ex) {
        LOGGER.info(ex.getMessage());
        return new ResponseEntity(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), null);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity handleForbidden(HttpServletRequest request, Throwable ex) {
        LOGGER.info(ex.getMessage());
        return new ResponseEntity(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), null);
    }

    @ExceptionHandler(WebErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleErrorException(HttpServletRequest request, Throwable ex) {
        LOGGER.info(ex.getMessage());
        return new ResponseEntity(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleBindErrorException(HttpServletRequest request, Throwable ex) {
        LOGGER.info(ex.getMessage());
        return new ResponseEntity(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity globalException(HttpServletRequest request, Throwable ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
    }

    /*private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }*/
}
