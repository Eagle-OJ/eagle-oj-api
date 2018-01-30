package com.eagleoj.web.service;

import com.eagleoj.judge.entity.ResponseEntity;
import com.eagleoj.web.judger.JudgeResult;
import com.eagleoj.web.judger.task.ProblemJudgeTask;

/**
 * @author Smith
 **/
public interface JudgeService {

    JudgeResult getJudgeResult(String id);

    void saveProblemCode(ProblemJudgeTask task, ResponseEntity response);

    void saveContestCode(ProblemJudgeTask task, ResponseEntity response);

    void saveGroupContestCode(ProblemJudgeTask task, ResponseEntity response);

}
