package com.eagleoj.web.judger;

import com.eagleoj.web.judger.task.JudgeTask;
import org.ehcache.Cache;
import com.eagleoj.web.cache.CacheController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Smith
 **/
@Component
public class JudgeQueue {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private Cache<String, JudgeResult> submissionCache;

    private PriorityBlockingQueue<JudgeTask> queue;

    JudgeQueue() {
        submissionCache = CacheController.getSubmissionCache();
        queue = new PriorityBlockingQueue<>(10, ((o1, o2) -> -(o1.getPriority() - o2.getPriority())));
    }

    public void addTask(JudgeTask task) {
        String id = task.getId();
        // 先放入缓存中
        JudgeResult result = new JudgeResult(id, JudgeStatus.InQueue, null, task);
        submissionCache.put(id, result);

        // 放入队列
        queue.put(task);;

    }

    public JudgeTask take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
