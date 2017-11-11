package org.inlighting.oj.web.controller.format.user;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class EnterContestFormat {

    @NotNull
    private int cid;

    private String password;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
