package org.inlighting.oj.judge.config;

import com.alibaba.fastjson.annotation.JSONField;

public class JudgeResponseBean {

    @JSONField(name = "problem_status")
    private ProblemStatusEnum problemStatusEnum;

    @JSONField(name = "test_case_number")
    private int testCaseNumber;

    /**
     * mb
     */
    private double memory;

    /**
     * 程序耗时 单位 sec
     */
    @JSONField(name = "real_time")
    private double realTime;

    /**
     * Compile error
     */
    private String[] stderr;

    private String[] output;

    @JSONField(name = "problem_status_list")
    private ProblemStatusEnum[] problemStatusEnums;

    private long dateline;

    public ProblemStatusEnum getProblemStatusEnum() {
        return problemStatusEnum;
    }

    public void setProblemStatusEnum(ProblemStatusEnum problemStatusEnum) {
        this.problemStatusEnum = problemStatusEnum;
    }

    public int getTestCaseNumber() {
        return testCaseNumber;
    }

    public void setTestCaseNumber(int testCaseNumber) {
        this.testCaseNumber = testCaseNumber;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public double getRealTime() {
        return realTime;
    }

    public void setRealTime(double realTime) {
        this.realTime = realTime;
    }

    public String[] getStderr() {
        return stderr;
    }

    public void setStderr(String[] stderr) {
        this.stderr = stderr;
    }

    public long getDateline() {
        return dateline;
    }

    public void setDateline(long dateline) {
        this.dateline = dateline;
    }

    public ProblemStatusEnum[] getProblemStatusEnums() {
        return problemStatusEnums;
    }

    public void setProblemStatusEnums(ProblemStatusEnum[] problemStatusEnums) {
        this.problemStatusEnums = problemStatusEnums;
    }

    public String[] getOutput() {
        return output;
    }

    public void setOutput(String[] output) {
        this.output = output;
    }
}
