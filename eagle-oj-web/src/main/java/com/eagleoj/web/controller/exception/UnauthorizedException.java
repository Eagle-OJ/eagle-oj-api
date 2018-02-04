package com.eagleoj.web.controller.exception;

/**
 * @author Smith
 **/
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("未被授权");
    }

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
