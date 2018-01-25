package com.eagleoj.web.postman.runner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eagleoj.web.postman.MessageTemplate;
import com.eagleoj.web.postman.task.*;
import com.eagleoj.web.postman.MessageQueue;
import com.eagleoj.web.postman.MessageTemplate;
import com.eagleoj.web.postman.task.*;
import com.eagleoj.web.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Smith
 **/
@Service
public class PostmanRunner {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final int MAX_THREADS = 1;

    private final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(MAX_THREADS);

    @Autowired
    private LeaderboardService leaderboardService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ContestUserService contestUserService;

    @Autowired
    private GroupUserService groupUserService;

    public PostmanRunner(MessageQueue messageQueue) {
        new Thread(() -> {
            while (true) {
                BaseTask task = messageQueue.take();
                THREAD_POOL.execute(new Runner(task));
            }

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
                    closeContest((CloseNormalContestTask) baseTask, false);
                    break;
                case 2:
                    closeOfficialContest((CloseOfficialContestTask) baseTask);
                    break;
                case 3:
                    pullGroupUserIntoContest((PullGroupUserIntoContestTask) baseTask);
                    break;
                case 4:
                    sendGroupUserMessage((SendGroupUserMessageTask) baseTask);
                    break;
                case 5:
                    sendProblemAcceptedMessage((SendProblemAcceptedMessageTask) baseTask);
                    break;
                case 6:
                    sendProblemRefusedMessage((SendProblemRefusedMessageTask) baseTask);
                    break;
                default:
                    LOGGER.error("Invalid postman task type:"+type);
            }
        }

        // type = 1
        private void closeContest(CloseNormalContestTask task, boolean isOfficial) {
            int cid = task.getCid();
            leaderboardService.refreshContestLeaderboard(cid);
            List<Map<String, Object>> leaderboard = leaderboardService.getContestLeaderboard(cid);
            Map<String, Object> contestMeta = leaderboard.get(0);
            String name = String.valueOf(contestMeta.get("name"));
            Map<String, Object> userRank = leaderboard.get(1);
            for (Map.Entry<String, Object> entry: userRank.entrySet()) {
                int uid = Integer.valueOf(entry.getKey());
                int rank = (int) entry.getValue();
                sendNormalMessage(uid, MessageTemplate.generateCloseContestMessage(cid, name, rank));
            }

            // 发送official全局比赛消息
            if (isOfficial) {
                int maxLength = leaderboard.size()>=5 ? 3: leaderboard.size()-2;
                JSONArray rankList = new JSONArray(maxLength);
                for (int i=2; i<maxLength+2; i++) {
                    JSONObject jsonObject = new JSONObject(2);
                    jsonObject.put("uid", leaderboard.get(i).get("uid"));
                    jsonObject.put("nickname", leaderboard.get(i).get("nickname"));
                    rankList.add(jsonObject);
                }
                messageService.addMessage(0, 2, "",
                        MessageTemplate.generateOfficialContestClosedMessage(cid, name, rankList));
            }

        }

        // type = 2 发送Official比赛结束的全局消息
        private void closeOfficialContest(CloseOfficialContestTask task) {
            CloseNormalContestTask newTask = new CloseNormalContestTask(task.getCid(), task.getType());
            closeContest(newTask, true);
        }

        // type = 3 将小组成员拉入比赛
        private void pullGroupUserIntoContest(PullGroupUserIntoContestTask task) {
            List<Map<String, Object>> users = groupUserService.getMembers(task.getGid(), null);
            for (Map<String, Object> user: users) {
                int uid = Long.valueOf((long)user.get("uid")).intValue();
                boolean i = contestUserService.add(task.getCid(), uid, System.currentTimeMillis());
                if (i) {
                    sendNormalMessage(uid,
                            MessageTemplate.generateUserPulledIntoContestMessage(task.getContestName(),
                            task.getCid(), task.getGroupName(), task.getGid()));
                }
            }
        }

        // type = 4 给小组成员发送通知
        private void sendGroupUserMessage(SendGroupUserMessageTask task) {
            List<Map<String, Object>> users = groupUserService.getMembers(task.getGid(), null);
            for (Map<String, Object> user: users) {
                int uid = Long.valueOf((long)user.get("uid")).intValue();
                sendNormalMessage(uid, MessageTemplate.generateSendGroupUserMessage(task.getGid(),
                        task.getGroupName(), task.getMessage()));
            }
        }

        // type = 5
        private void sendProblemAcceptedMessage(SendProblemAcceptedMessageTask task) {
            int uid = task.getUid();
            sendNormalMessage(uid, MessageTemplate.generateSendProblemAcceptedMessage(task.getTitle(),
                    task.getPid()));
        }

        // type = 6
        private void sendProblemRefusedMessage(SendProblemRefusedMessageTask task) {
            int uid = task.getUid();
            sendNormalMessage(uid, MessageTemplate.generateSendProblemRefusedMessage(task.getTitle(),
                    task.getPid()));
        }

        private void sendNormalMessage(int uid, String message) {
            messageService.addMessage(uid, 0, message, null);
        }
    }
}
