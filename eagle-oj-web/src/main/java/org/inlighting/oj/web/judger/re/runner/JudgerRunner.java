package org.inlighting.oj.web.judger.re.runner;

import org.ehcache.Cache;
import org.inlighting.oj.judge.entity.RequestEntity;
import org.inlighting.oj.judge.entity.ResponseEntity;
import org.inlighting.oj.judge.judger.Judger;
import org.inlighting.oj.judge.judger.judge0.Judge0;
import org.inlighting.oj.web.cache.CacheController;
import org.inlighting.oj.web.judger.re.JudgerQueue;
import org.inlighting.oj.web.judger.re.JudgerResult;
import org.inlighting.oj.web.judger.re.JudgerStatus;
import org.inlighting.oj.web.judger.re.JudgerTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Smith
 **/
@Component
public class JudgerRunner {
    private final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(3);

    private final Cache<String, JudgerResult> submissionCache = CacheController.getSubmissionCache();

    @Value("${eagle-oj.judge.url}")
    private String JUDGE_URL;

    public JudgerRunner(JudgerQueue queue) {
        new Thread(() -> {
            while (true) {
                JudgerTask judgerTask = queue.take();
                THREAD_POOL.execute(new Runner(judgerTask));
            }
        }).start();
    }

    class Runner implements Runnable {

        private JudgerTask judgerTask;

        Runner(JudgerTask task) {
            this.judgerTask = task;
        }

        @Override
        public void run() {
            String id = judgerTask.getId();
            JudgerResult judgerResult = submissionCache.get(id);
            // 更改正在判卷状态
            judgerResult.setStatus(JudgerStatus.Judging);
            // 组装判卷格式
            RequestEntity requestEntity = new RequestEntity(judgerTask.getLang(), judgerTask.getSourceCode(),
                    3, 128, judgerTask.getTestCases());

            Judger judger = new Judger(JUDGE_URL, requestEntity, new Judge0());
            ResponseEntity response = judger.judge();
            // 判卷完成
            judgerResult.setResponse(response);

            // 进行数据的保存
            if (! judgerTask.isTestMode()){
                judgerResult.setStatus(JudgerStatus.Saving);
                save(judgerTask, judgerResult);
            }
            judgerResult.setStatus(JudgerStatus.Finished);
        }

        private void save(JudgerTask task, JudgerResult result) {
            // save
        }
    }
}
