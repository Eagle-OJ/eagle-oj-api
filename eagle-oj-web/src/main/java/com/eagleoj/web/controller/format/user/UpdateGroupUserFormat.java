package com.eagleoj.web.controller.format.user;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class UpdateGroupUserFormat {

    @NotNull
    @Length(max = 20)
    @JSONField(name = "group_name")
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
