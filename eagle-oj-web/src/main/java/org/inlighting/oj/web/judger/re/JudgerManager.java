package org.inlighting.oj.web.judger.re;

import org.ehcache.Cache;
import org.inlighting.oj.judge.LanguageEnum;
import org.inlighting.oj.judge.entity.TestCaseRequestEntity;
import org.inlighting.oj.web.cache.CacheController;
import org.inlighting.oj.web.judger.Judger;
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
                                 LanguageEnum lang,
                                 String sourceCode,
                                 List<TestCaseRequestEntity> testCases) {
        JudgerTask task = new JudgerTask();
        task.setTestMode(testMode);
        task.setProblemId(problemId);
        task.setContestId(contestId);
        task.setLang(lang);
        task.setSourceCode(sourceCode);
        task.setTestCases(testCases);
        return judgerQueue.addTask(task);
    }

    public JudgerResult getTask(String id) {
        return submissionCache.get(id);
    }


}
