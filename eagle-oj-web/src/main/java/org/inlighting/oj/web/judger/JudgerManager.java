package org.inlighting.oj.web.judger;

import org.ehcache.Cache;
import org.inlighting.oj.judge.LanguageEnum;
import org.inlighting.oj.judge.entity.TestCaseRequestEntity;
import org.inlighting.oj.web.cache.CacheController;
import org.inlighting.oj.web.entity.ContestEntity;
import org.inlighting.oj.web.entity.ContestProblemEntity;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Smith
 **/
@Component
public class JudgerManager {

    private JudgerQueue judgerQueue;

    private Cache<String, JudgerResult> submissionCache = CacheController.getSubmissionCache();

    public JudgerManager(JudgerQueue judgerQueue) {
        this.judgerQueue = judgerQueue;
    }

    public String addTask(boolean testMode,
                          int problemId,
                          int contestId,
                          int owner,
                          LanguageEnum lang,
                          String sourceCode,
                          List<TestCaseRequestEntity> testCases,
                          List<TestCaseEntity> addTestCaseEntities,
                          ProblemEntity addProblemEntity,
                          ContestEntity addContestEntity,
                          ContestProblemEntity addContestProblemEntity) {
        JudgerTask task = new JudgerTask();
        task.setTestMode(testMode);
        task.setProblemId(problemId);
        task.setContestId(contestId);
        task.setOwner(owner);
        task.setLang(lang);
        task.setSourceCode(sourceCode);
        task.setTestCases(testCases);
        task.setAddTestCaseEntities(addTestCaseEntities);
        task.setAddProblemEntity(addProblemEntity);
        task.setAddContestEntity(addContestEntity);
        task.setAddContestProblemEntity(addContestProblemEntity);
        return judgerQueue.addTask(task);
    }

    public JudgerResult getTask(String id) {
        return submissionCache.get(id);
    }


}
