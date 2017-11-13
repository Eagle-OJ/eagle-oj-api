package org.inlighting.oj.web.controller.format.user;

import com.alibaba.fastjson.JSONArray;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class AddContestProblemFormat {

    @NotNull
    private JSONArray problems;

    public JSONArray getProblems() {
        return problems;
    }

    public void setProblems(JSONArray problems) {
        this.problems = problems;
    }
}
