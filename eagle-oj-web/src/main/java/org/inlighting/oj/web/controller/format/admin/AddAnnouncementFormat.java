package org.inlighting.oj.web.controller.format.admin;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class AddAnnouncementFormat {

    @NotNull
    @Length(max = 50)
    private String title;

    @NotNull
    @Length(max = 500)
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
