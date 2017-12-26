package org.inlighting.oj.web.controller.exception;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.inlighting.oj.web.controller.exception.UnauthorizedException;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
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
        return new ResponseEntity(400, error.getField()+": "+error.getDefaultMessage(), null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handle(ConstraintViolationException e) {
        String s = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()).get(0);
        return new ResponseEntity(400, s, null);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handle(HttpServletRequest request, Throwable ex) {
        LOGGER.info(ex.getMessage());
        return new ResponseEntity(401, ex.getMessage(), null);
    }

    @ExceptionHandler(WebErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleErrorException(HttpServletRequest request, Throwable ex) {
        LOGGER.info(ex.getMessage());
        return new ResponseEntity(400, ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity globalException(HttpServletRequest request, Throwable ex) {
        LOGGER.info(ex.getMessage(), ex);
        return new ResponseEntity(500, ex.getMessage(), null);
    }

    /*private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }*/
}
