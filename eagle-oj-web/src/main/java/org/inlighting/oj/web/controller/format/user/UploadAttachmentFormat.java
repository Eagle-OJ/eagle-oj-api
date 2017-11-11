package org.inlighting.oj.web.controller.format.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class UploadAttachmentFormat {

    @NotNull
    @Length(min = 1, max = 255)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
