package com.eagleoj.web.controller.exception;

/**
 * @author Smith
 **/
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("禁止访问");
    }

    public ForbiddenException(String msg) {
        super(msg);
    }
}
