package com.eagleoj.web.judger.runner;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.judge.entity.RequestEntity;
import com.eagleoj.judge.entity.ResponseEntity;
import com.eagleoj.judge.entity.TestCaseRequestEntity;
import com.eagleoj.judge.judger.Judger;
import com.eagleoj.judge.judger.eagle.Eagle;
import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.entity.TestCaseEntity;
import com.eagleoj.web.judger.JudgeQueue;
import com.eagleoj.web.judger.JudgeResult;
import com.eagleoj.web.judger.JudgeStatus;
import com.eagleoj.web.judger.task.*;
import com.eagleoj.web.service.JudgeService;
import com.eagleoj.web.service.SettingService;
import org.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Smith
 **/
@Component
public class JudgeRunner {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final int MAX_THREADS = 3;

    private final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(MAX_THREADS);

    private final Cache<String, JudgeResult> submissionCache = CacheController.getSubmissionCache();

    private JudgeService judgeService;

    private SettingService settingService;

    public JudgeRunner(JudgeQueue queue, SettingService settingService, JudgeService judgeService) {
        this.settingService = settingService;
        this.judgeService = judgeService;
        new Thread(() -> {
            while (true) {
                JudgeTask judgeTask = queue.take();
                try {
                    THREAD_POOL.execute(new Runner(judgeTask));
                } catch (Exception e) {
                    submissionCache.get(judgeTask.getId()).setStatus(JudgeStatus.Error);
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    private String getJudgerUrl() {
        return settingService.getSystemConfig().getJudgerUrl();
    }


    class Runner implements Runnable {

        private JudgeTask judgeTask;

        private JudgeResult judgeResult;

        Runner(JudgeTask task) {
            this.judgeTask = task;
            this.judgeResult = submissionCache.get(judgeTask.getId());
        }

        @Override
        public void run() {
            // 正在判卷
            judgeResult.setStatus(JudgeStatus.Judging);

            LanguageEnum lang = judgeTask.getLang();
            String sourceCode = judgeTask.getSourceCode();
            int time = judgeTask.getTime();
            int memory =judgeTask.getMemory();
            List<TestCaseRequestEntity> testCases = new ArrayList<>(judgeTask.getTestCases().size());
            for (TestCaseEntity entity: judgeTask.getTestCases()) {
                TestCaseRequestEntity requestEntity = new TestCaseRequestEntity(entity.getStdin(), entity.getStdout());
                testCases.add(requestEntity);
            }
            RequestEntity requestEntity = new RequestEntity(lang, sourceCode, time, memory, testCases);
            String judgerUrl = getJudgerUrl();
            if (judgerUrl == null) {
                setJudgeFailed();
                LOGGER.error("判卷地址不得为空");
            }
            Judger judger = new Judger(judgerUrl, requestEntity, new Eagle());
            ResponseEntity responseEntity = judger.judge();
            if (responseEntity.getResult() == ResultEnum.SE) {
                setJudgeFailed();
            }
            judgeResult.setResponse(responseEntity);
            save();
        }

        private void save() {
            // 正在保存
            judgeResult.setStatus(JudgeStatus.Saving);

            if (judgeTask instanceof GroupJudgeTask) {
                LOGGER.info("Saving group");
                judgeService.saveGroupContestCode((GroupJudgeTask) judgeTask, judgeResult.getResponse());
            } else if (judgeTask instanceof ContestJudgeTask) {
                LOGGER.info("Saving contest");
                judgeService.saveContestCode((ContestJudgeTask) judgeTask, judgeResult.getResponse());
            } else if (judgeTask instanceof ProblemJudgeTask) {
                LOGGER.info("Saving problem");
                judgeService.saveProblemCode((ProblemJudgeTask) judgeTask, judgeResult.getResponse());
            } else {
                // do nothing
                LOGGER.info("Saving test");
            }
            judgeResult.setStatus(JudgeStatus.Finished);
        }

        private void setJudgeFailed() {
            judgeResult.setStatus(JudgeStatus.Error);
        }
    }
}
