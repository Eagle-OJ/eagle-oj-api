package org.inlighting.oj.web.controller.format.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class SendGroupUserMessageFormat {

    @NotNull
    @Length(min = 9, max = 400)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
