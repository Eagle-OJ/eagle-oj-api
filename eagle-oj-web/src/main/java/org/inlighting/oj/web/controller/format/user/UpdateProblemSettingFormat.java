package org.inlighting.oj.web.controller.format.user;

import com.alibaba.fastjson.JSONArray;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class UpdateProblemSettingFormat {

    @NotNull
    private JSONArray lang;

    @NotNull
    @Range(min = 3, max = 10)
    private Integer time;

    @NotNull
    @Range(min = 1, max = 256)
    private Integer memory;

    public JSONArray getLang() {
        return lang;
    }

    public void setLang(JSONArray lang) {
        this.lang = lang;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }
}
