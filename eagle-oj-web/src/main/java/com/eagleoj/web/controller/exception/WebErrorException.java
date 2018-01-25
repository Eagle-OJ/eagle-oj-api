package com.eagleoj.web.controller.exception;

/**
 * @author Smith
 **/
public class WebErrorException extends RuntimeException {
    public WebErrorException() {
        super("本次操作非法");
    }

    public WebErrorException(String message) {
        super(message);
    }
}
