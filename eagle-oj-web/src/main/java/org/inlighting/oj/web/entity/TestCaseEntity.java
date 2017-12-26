package org.inlighting.oj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class TestCaseEntity {

    private int tid;

    private int pid;

    private String stdin;

    private String stdout;

    private int strength;

    @JSONField(name = "create_time")
    private long createTime;


    public TestCaseEntity() {
    }

    public TestCaseEntity(int pid, String stdin, String stdout, int strength, long createTime) {
        this.pid = pid;
        this.stdin = stdin;
        this.stdout = stdout;
        this.strength = strength;
        this.createTime = createTime;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getStdin() {
        return stdin;
    }

    public void setStdin(String stdin) {
        this.stdin = stdin;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
