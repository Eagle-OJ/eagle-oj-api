package com.eagleoj.web.postman;

import com.eagleoj.web.postman.task.BaseTask;
import com.eagleoj.web.postman.task.BaseTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Smith
 **/
@Component
public class MessageQueue {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private BlockingQueue<BaseTask> messageQueue;

    public MessageQueue() {
        messageQueue = new LinkedBlockingQueue<>();
    }

    public void addTask(BaseTask task) {
        messageQueue.add(task);
    }

    public BaseTask take() {
        try {
            return messageQueue.take();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
