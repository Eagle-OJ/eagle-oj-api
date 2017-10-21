package org.inlighting.oj.judge.bean;

public class StdResponseBean {

    private boolean isAccept;

    private int testCaseNumber;

    /**
     * kb
     */
    private int memory;

    /**
     * 程序耗时 单位 sec
     */
    private double realTime;

    /**
     * Sandbox error
     */
    private String[] message;

    /**
     * Compile error
     */
    private String[] stderr;

    /**
     * 返回结果，例如accepted，error之类
     */
    private String[] result;

    private long dateline;

    public String[] getResult() {
        return result;
    }

    public void setResult(String[] result) {
        this.result = result;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }

    public int getTestCaseNumber() {
        return testCaseNumber;
    }

    public void setTestCaseNumber(int testCaseNumber) {
        this.testCaseNumber = testCaseNumber;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public double getRealTime() {
        return realTime;
    }

    public void setRealTime(double realTime) {
        this.realTime = realTime;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
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

}
