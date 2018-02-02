package com.eagleoj.web.postman.runner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eagleoj.web.entity.GroupUserEntity;
import com.eagleoj.web.postman.MessageTemplate;
import com.eagleoj.web.postman.MessageTypeEnum;
import com.eagleoj.web.postman.task.*;
import com.eagleoj.web.postman.TaskQueue;
import com.eagleoj.web.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ContestService contestService;

    @Autowired
    private GroupUserService groupUserService;

    public PostmanRunner(TaskQueue messageQueue) {
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
            if (baseTask instanceof CloseNormalContestTask) {
                closeContest((CloseNormalContestTask) baseTask, false);
            } else if (baseTask instanceof CloseOfficialContestTask) {
                closeOfficialContest((CloseOfficialContestTask) baseTask);
            } else if (baseTask instanceof  SendGroupUserMessageTask) {
                sendGroupUserMessage((SendGroupUserMessageTask) baseTask);
            } else if (baseTask instanceof SendProblemAcceptedMessageTask) {
                sendProblemAcceptedMessage((SendProblemAcceptedMessageTask) baseTask);
            } else if (baseTask instanceof SendProblemRefusedMessageTask) {
                sendProblemRefusedMessage((SendProblemRefusedMessageTask) baseTask);
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
                messageService.save(0, MessageTypeEnum.GLOBAL_CONTEST.getNumber(), "",
                        MessageTemplate.generateOfficialContestClosedMessage(cid, name, rankList));
            }

        }

        // type = 2 发送Official比赛结束的全局消息
        private void closeOfficialContest(CloseOfficialContestTask task) {
            CloseNormalContestTask newTask = new CloseNormalContestTask(task.getCid());
            closeContest(newTask, true);
        }


        // type = 4 给小组成员发送通知
        private void sendGroupUserMessage(SendGroupUserMessageTask task) {
            List<GroupUserEntity> users = groupUserService.listGroupMembers(task.getGid());
            for (GroupUserEntity user: users) {
                int uid = user.getUid();
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
            messageService.save(uid, MessageTypeEnum.NORMAL.getNumber(), message, null);
        }
    }
}
