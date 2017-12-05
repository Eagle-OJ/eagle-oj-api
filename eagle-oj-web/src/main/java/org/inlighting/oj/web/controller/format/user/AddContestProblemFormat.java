package org.inlighting.oj.web.controller.format.user;

import com.alibaba.fastjson.JSONArray;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class AddContestProblemFormat {

    @NotNull
    @Range(min = 1)
    private Integer pid;

    @NotNull
    @Range(min = 1)
    private Integer score;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
