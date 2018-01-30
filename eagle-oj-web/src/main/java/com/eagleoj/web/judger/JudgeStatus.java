package com.eagleoj.web.judger;

/**
 * @author Smith
 **/
public enum JudgeStatus {
    InQueue("排队中"), Judging("正在判卷"), Saving("保存中"), Finished("完成"), Error("错误");

    private String message;

    private JudgeStatus(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
