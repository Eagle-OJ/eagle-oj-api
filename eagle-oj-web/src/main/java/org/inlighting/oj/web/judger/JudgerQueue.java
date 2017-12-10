package org.inlighting.oj.web.judger;

import org.ehcache.Cache;
import org.inlighting.oj.web.cache.CacheController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Smith
 **/
@Component
public class JudgerQueue {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private Cache<String, JudgerResult> submissionCache;

    private PriorityBlockingQueue<JudgerTask> queue;

    JudgerQueue() {
        submissionCache = CacheController.getSubmissionCache();
        queue = new PriorityBlockingQueue<>(10, ((o1, o2) -> -(o1.getPriority() - o2.getPriority())));
    }

    public String addTask(JudgerTask task) {
        String id = UUID.randomUUID().toString();
        // 先放入缓存中
        JudgerResult result = new JudgerResult(id, JudgerStatus.InQueue, null, task);
        submissionCache.put(id, result);

        // 放入队列中
        task.setId(id);
        queue.put(task);;

        return id;
    }

    public JudgerTask take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
