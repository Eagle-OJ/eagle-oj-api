package com.eagleoj.judge.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.eagleoj.judge.ResultEnum;

import java.util.List;

/**
 * @author Smith
 **/
public class ResponseEntity {

    public ResponseEntity(double time, int memory, ResultEnum result, List<TestCaseResponseEntity> testCases) {
        this.time = time;
        this.memory = memory;
        this.result = result;
        this.testCases = testCases;
    }

    public ResponseEntity() {
    }

    private double time;

    private int memory;

    private ResultEnum result;

    @JSONField(name = "test_cases")
    private List<TestCaseResponseEntity> testCases;

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public ResultEnum getResult() {
        return result;
    }

    public void setResult(ResultEnum result) {
        this.result = result;
    }

    public List<TestCaseResponseEntity> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCaseResponseEntity> testCases) {
        this.testCases = testCases;
    }
}
