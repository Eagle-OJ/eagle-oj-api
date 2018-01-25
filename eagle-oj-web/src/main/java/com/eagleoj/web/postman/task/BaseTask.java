package com.eagleoj.web.postman.task;

/**
 * @author Smith
 **/
public abstract class BaseTask {
    /**
     * type = 1 关闭比赛任务
     */
    protected int type = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
