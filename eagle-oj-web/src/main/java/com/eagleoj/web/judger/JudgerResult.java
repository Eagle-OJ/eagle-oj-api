package com.eagleoj.web.judger;


import com.eagleoj.judge.entity.ResponseEntity;

/**
 * @author Smith
 **/
public class JudgerResult {
    private String id;

    private JudgerStatus status;

    private ResponseEntity response;

    private JudgerTask judgerTask;

    public JudgerResult(String id, JudgerStatus status, ResponseEntity response, JudgerTask judgerTask) {
        this.id = id;
        this.status = status;
        this.response = response;
        this.judgerTask = judgerTask;
    }

    public JudgerTask getJudgerTask() {
        return judgerTask;
    }

    public void setJudgerTask(JudgerTask judgerTask) {
        this.judgerTask = judgerTask;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JudgerStatus getStatus() {
        return status;
    }

    public void setStatus(JudgerStatus status) {
        this.status = status;
    }

    public ResponseEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }
}
