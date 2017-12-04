package org.inlighting.oj.web.controller.format.user;

import com.alibaba.fastjson.JSONArray;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class AddContestProblemFormat {

    @Range(min = 1)
    private int pid;

    @Range(min = 1)
    private int score;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
