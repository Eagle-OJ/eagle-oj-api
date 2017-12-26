package org.inlighting.oj.web.postman.runner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.inlighting.oj.web.postman.MessageQueue;
import org.inlighting.oj.web.postman.MessageTemplate;
import org.inlighting.oj.web.postman.task.BaseTask;
import org.inlighting.oj.web.postman.task.CloseContestTask;
import org.inlighting.oj.web.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Smith
 **/
@Component
public class PostmanRunner {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final int MAX_THREADS = 1;

    private final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(MAX_THREADS);

    private ContestService contestService;

    private UserService userService;

    private LeaderboardService leaderboardService;

    private MessageService messageService;

    private ContestUserService contestUserService;

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setLeaderboardService(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Autowired
    public void setContestUserService(ContestUserService contestUserService) {
        this.contestUserService = contestUserService;
    }

    public PostmanRunner(MessageQueue messageQueue) {
        new Thread(() -> {
            BaseTask task = messageQueue.take();
            THREAD_POOL.execute(new Runner(task));
        }).start();
    }

    class Runner implements Runnable {

        private BaseTask baseTask;

        Runner(BaseTask task) {
            this.baseTask = task;
        }

        @Override
        public void run() {
            int type = baseTask.getType();
            switch (type) {
                case 1:
                    closeContest((CloseContestTask) baseTask);
                    break;
                default:
                    LOGGER.error("Invalid postman task");
            }
        }

        // type = 1
        private void closeContest(CloseContestTask task) {
            int cid = task.getCid();
            leaderboardService.refreshContestLeaderboard(cid);
            List<Map<String, Object>> leaderboard = leaderboardService.getContestLeaderboard(cid);
            Map<String, Object> contestMeta = leaderboard.get(0);
            String name = String.valueOf(contestMeta.get("name"));
            Map<String, Object> userRank = leaderboard.get(1);
            for (Map.Entry<String, Object> entry: userRank.entrySet()) {
                int uid = Integer.valueOf(entry.getKey());
                int rank = (int) entry.getValue();
                messageService.addMessage(uid, 1,
                        MessageTemplate.generateCloseContestMessage(cid, name, rank), new JSONObject());
            }
        }
    }
}
