package com.eagleoj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class TestCaseEntity {

    private Integer tid;

    private Integer pid;

    private String stdin;

    private String stdout;

    private Integer strength;

    @JSONField(name = "create_time")
    private Long createTime;


    public TestCaseEntity() {
    }

    public TestCaseEntity(Integer pid, String stdin, String stdout, Integer strength, Long createTime) {
        this.pid = pid;
        this.stdin = stdin;
        this.stdout = stdout;
        this.strength = strength;
        this.createTime = createTime;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
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

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
