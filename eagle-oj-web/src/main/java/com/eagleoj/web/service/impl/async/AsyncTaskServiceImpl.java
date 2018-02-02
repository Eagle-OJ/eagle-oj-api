package com.eagleoj.web.service.impl.async;

import com.eagleoj.web.postman.TaskQueue;
import com.eagleoj.web.postman.task.*;
import com.eagleoj.web.service.async.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

    @Autowired
    private TaskQueue taskQueue;

    @Override
    public void closeNormalContest(int cid) {
        CloseNormalContestTask task = new CloseNormalContestTask(cid);
        taskQueue.addTask(task);
    }

    @Override
    public void closeOfficialContest(int cid) {
        CloseOfficialContestTask task = new CloseOfficialContestTask(cid);
        taskQueue.addTask(task);
    }

    @Override
    public void sendGroupMessage(String message, String groupName, int gid) {
        SendGroupUserMessageTask task = new SendGroupUserMessageTask(gid, groupName,
                message);
        taskQueue.addTask(task);
    }

    @Override
    public void sendAcceptAuditingProblem(String title, int owner, int pid) {
        SendProblemAcceptedMessageTask task = new SendProblemAcceptedMessageTask();
        task.setTitle(title);
        task.setUid(owner);
        task.setPid(pid);
        addTask(task);
    }

    @Override
    public void sendRefuseAuditingProblem(String title, int owner, int pid) {
        SendProblemAcceptedMessageTask task = new SendProblemAcceptedMessageTask();
        task.setTitle(title);
        task.setUid(owner);
        task.setPid(pid);
        addTask(task);
    }


    private void addTask(BaseTask task) {
        taskQueue.addTask(task);
    }
}
