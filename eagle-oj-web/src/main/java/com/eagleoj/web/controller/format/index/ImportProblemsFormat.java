package com.eagleoj.web.controller.format.index;

import com.alibaba.fastjson.JSONArray;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class ImportProblemsFormat {

    @NotNull
    private JSONArray pidList;

    public JSONArray getPidList() {
        return pidList;
    }

    public void setPidList(JSONArray pidList) {
        this.pidList = pidList;
    }
}
