package org.inlighting.oj.web.postman.task;

/**
 * @author Smith
 **/
public class CloseOfficialContestTask extends BaseTask {

    private int cid;

    public CloseOfficialContestTask(int cid, int type) {
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
