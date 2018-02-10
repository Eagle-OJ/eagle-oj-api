package com.eagleoj.judge.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.eagleoj.judge.ResultEnum;

/**
 * @author Smith
 **/
public class TestCaseResponseEntity {

    public TestCaseResponseEntity(ResultEnum result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public TestCaseResponseEntity() {
    }

    private ResultEnum result;

    @JSONField(name = "error_message")
    private String errorMessage;

    public ResultEnum getResult() {
        return result;
    }

    public void setResult(ResultEnum result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
