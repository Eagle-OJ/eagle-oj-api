package com.eagleoj.web.postman.task;

/**
 * @author Smith
 **/
public class CloseNormalContestTask extends BaseTask {
    private int cid;

    public CloseNormalContestTask(int cid, int type) {
        this.cid = cid;
        this.type = type;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
