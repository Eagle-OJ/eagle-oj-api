package com.eagleoj.web.service;

import com.eagleoj.judge.entity.ResponseEntity;
import com.eagleoj.web.judger.JudgeResult;
import com.eagleoj.web.judger.task.ContestJudgeTask;
import com.eagleoj.web.judger.task.GroupJudgeTask;
import com.eagleoj.web.judger.task.ProblemJudgeTask;

/**
 * @author Smith
 **/
public interface JudgeService {

    JudgeResult getJudgeResult(String id);

    void saveProblemCode(ProblemJudgeTask task, ResponseEntity response);

    void saveContestCode(ContestJudgeTask task, ResponseEntity response);

    void saveGroupContestCode(GroupJudgeTask task, ResponseEntity response);

}
