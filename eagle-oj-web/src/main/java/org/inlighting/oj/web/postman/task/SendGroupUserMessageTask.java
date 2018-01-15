package org.inlighting.oj.web.postman.task;

/**
 * @author Smith
 **/
public class SendGroupUserMessageTask extends BaseTask {
    private int gid;

    private String groupName;

    private String message;

    public SendGroupUserMessageTask(int gid, String groupName, String message) {
        this.gid = gid;
        this.groupName = groupName;
        this.message = message;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
