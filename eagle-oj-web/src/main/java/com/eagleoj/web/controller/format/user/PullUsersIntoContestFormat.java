package com.eagleoj.web.controller.format.user;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class PullUsersIntoContestFormat {

    @Length(min = 1, max = 6)
    private String password;

    @NotNull
    @Range(min = 1)
    private Integer cid;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}
