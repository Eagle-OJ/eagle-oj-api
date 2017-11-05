package org.inlighting.oj.web.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Smith
 **/
@RestControllerAdvice
public class GlobalExceptionController {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity globalException(HttpServletRequest request, Throwable ex) {
        LOGGER.info(ex.getMessage(), ex);
        return new ResponseEntity(getStatus(request).value(), ex.getMessage(), null);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
