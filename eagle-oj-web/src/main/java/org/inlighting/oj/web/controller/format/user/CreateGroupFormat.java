package org.inlighting.oj.web.controller.format.user;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class CreateGroupFormat {

    @NotNull
    @Range(min = 0)
    private Integer cover;

    @NotNull
    @Length(min = 1, max = 20)
    private String name;

    @Length(max = 6)
    private String password;

    public Integer getCover() {
        return cover;
    }

    public void setCover(Integer cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
