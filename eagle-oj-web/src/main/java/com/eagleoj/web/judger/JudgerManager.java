package com.eagleoj.web.judger;

import org.ehcache.Cache;
import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.entity.TestCaseRequestEntity;
import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class JudgerManager {

   /* @Autowired
    private JudgerQueue judgerQueue;

    private Cache<String, JudgeResult> submissionCache = CacheController.getSubmissionCache();

    public String addTask(boolean testMode,
                          int problemId,
                          int contestId,
                          int owner,
                          LanguageEnum lang,
                          String sourceCode,
                          int time,
                          int memory,
                          List<TestCaseRequestEntity> testCases,
                          List<TestCaseEntity> addTestCaseEntities,
                          ProblemEntity addProblemEntity,
                          ContestEntity addContestEntity,
                          ContestProblemEntity addContestProblemEntity,
                          ContestUserEntity addContestUserEntity) {
        JudgerTask task = new JudgerTask();
        task.setTestMode(testMode);
        task.setProblemId(problemId);
        task.setContestId(contestId);
        task.setOwner(owner);
        task.setLang(lang);
        task.setSourceCode(sourceCode);
        task.setTime(time);
        task.setMemory(memory);
        task.setTestCases(testCases);
        task.setAddTestCaseEntities(addTestCaseEntities);
        task.setAddProblemEntity(addProblemEntity);
        task.setAddContestEntity(addContestEntity);
        task.setAddContestProblemEntity(addContestProblemEntity);
        task.setAddContestUserEntity(addContestUserEntity);
        return judgerQueue.addTask(task);
    }

    public JudgeResult getTask(String id) {
        return submissionCache.get(id);
    }

*/
}
