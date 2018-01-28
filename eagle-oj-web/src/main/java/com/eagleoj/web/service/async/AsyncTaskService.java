package com.eagleoj.web.service.async;

import com.eagleoj.web.postman.task.BaseTask;

/**
 * @author Smith
 **/
public interface AsyncTaskService {

    void closeOfficialContest(int cid);

    void closeNormalContest(int cid);

    void sendAcceptAuditingProblem(String title, int owner, int pid);

    void sendRefuseAuditingProblem(String title, int owner, int pid);
}
