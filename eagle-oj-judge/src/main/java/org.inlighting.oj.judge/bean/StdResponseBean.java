package org.inlighting.oj.judge.bean;

import org.inlighting.oj.judge.config.ProblemStatusEnum;

public class StdResponseBean {

    private ProblemStatusEnum problemStatusEnum;

    private int testCaseNumber;

    /**
     * mb
     */
    private double memory;

    /**
     * 程序耗时 单位 sec
     */
    private double realTime;

    /**
     * Compile error
     */
    private String[] stderr;

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
}
