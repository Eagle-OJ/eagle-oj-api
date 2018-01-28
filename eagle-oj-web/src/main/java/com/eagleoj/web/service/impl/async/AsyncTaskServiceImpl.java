package com.eagleoj.web.service.impl.async;

import com.eagleoj.web.postman.TaskQueue;
import com.eagleoj.web.postman.task.BaseTask;
import com.eagleoj.web.postman.task.CloseNormalContestTask;
import com.eagleoj.web.postman.task.CloseOfficialContestTask;
import com.eagleoj.web.postman.task.SendProblemAcceptedMessageTask;
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
        CloseNormalContestTask task = new CloseNormalContestTask(cid, 1);
        taskQueue.addTask(task);
    }

    @Override
    public void closeOfficialContest(int cid) {
        CloseOfficialContestTask task = new CloseOfficialContestTask(cid, 2);
        taskQueue.addTask(task);
    }

    @Override
    public void sendAcceptAuditingProblem(String title, int owner, int pid) {
        SendProblemAcceptedMessageTask task = new SendProblemAcceptedMessageTask();
        task.setTitle(title);
        task.setUid(owner);
        task.setPid(pid);
        task.setType(5);
        addTask(task);
    }

    @Override
    public void sendRefuseAuditingProblem(String title, int owner, int pid) {
        SendProblemAcceptedMessageTask task = new SendProblemAcceptedMessageTask();
        task.setTitle(title);
        task.setUid(owner);
        task.setPid(pid);
        task.setType(6);
        addTask(task);
    }

    private void addTask(BaseTask task) {
        taskQueue.addTask(task);
    }
}
