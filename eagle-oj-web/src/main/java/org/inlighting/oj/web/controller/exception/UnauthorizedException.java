package org.inlighting.oj.web.controller.exception;

/**
 * @author Smith
 **/
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("非法操作");
    }

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
