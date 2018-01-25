package com.eagleoj.web.judger.runner;

import com.eagleoj.web.judger.JudgerQueue;
import com.eagleoj.web.judger.JudgerTask;
import com.eagleoj.web.service.ProblemUserService;
import org.ehcache.Cache;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.judge.entity.RequestEntity;
import com.eagleoj.judge.entity.ResponseEntity;
import com.eagleoj.judge.entity.TestCaseResponseEntity;
import com.eagleoj.judge.judger.Judger;
import com.eagleoj.judge.judger.judge0.Judge0;
import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.entity.*;
import com.eagleoj.web.judger.JudgerQueue;
import com.eagleoj.web.judger.JudgerResult;
import com.eagleoj.web.judger.JudgerStatus;
import com.eagleoj.web.judger.JudgerTask;
import com.eagleoj.web.service.*;
import com.eagleoj.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Smith
 **/
@Service
public class JudgerRunner {

    private final int MAX_THREADS = 3;

    private final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(MAX_THREADS);

    private final Cache<String, JudgerResult> submissionCache = CacheController.getSubmissionCache();

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ProblemUserService problemUserService;

    @Autowired
    private ContestProblemService contestProblemService;

    @Autowired
    private ContestProblemUserService contestProblemUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private ContestUserService contestUserService;

    @Autowired
    private FileUtil fileUtil;

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
                    judgerTask.getTime(), judgerTask.getMemory(), judgerTask.getTestCases());

            Judger judger = new Judger(JUDGE_URL, requestEntity, new Judge0());
            ResponseEntity response = judger.judge();
            // 判卷完成
            judgerResult.setResponse(response);

            // 判卷错误直接返回
            if (judgerResult.getResponse().getResult() == ResultEnum.SE) {
                judgerResult.setStatus(JudgerStatus.Error);
                return;
            }

            // 进行数据的保存
            if (! judgerTask.isTestMode()){
                judgerResult.setStatus(JudgerStatus.Saving);
                save(judgerTask, judgerResult);
            }
            judgerResult.setStatus(JudgerStatus.Finished);
        }

        private void save(JudgerTask task, JudgerResult result) {
            saveSubmission(task, result);
            saveUserLog(task.getOwner(), result.getResponse().getResult());
            int contestId = task.getContestId();
            if (contestId == 0) {
                saveProblem(task, result);
            } else {
                saveContestProblem(task, result);
            }
        }

        private void saveSubmission(JudgerTask task, JudgerResult result) {
            int uid = task.getOwner();
            String filePath = fileUtil.uploadCode(task.getLang(), task.getSourceCode());
            int aid = attachmentService.add(uid, filePath);
            submissionService.add(uid, task.getProblemId(), task.getContestId(),
                    aid, task.getLang(), result.getResponse().getTime(), result.getResponse().getMemory(),
                    result.getResponse().getResult());
        }

        // 保存普通代码提交
        private void saveProblem(JudgerTask task, JudgerResult result) {
            int pid = task.getProblemId();
            int uid = task.getOwner();
            int status = task.getAddProblemEntity().getStatus();
            ResultEnum resultEnum = result.getResponse().getResult();
            ProblemUserEntity problemUserEntity = problemUserService.get(pid, uid);
            if (problemUserEntity == null) {
                problemUserService.add(pid, uid, resultEnum);
                updateUserTimes(uid, status, resultEnum);
                updateProblemTimes(pid, resultEnum);
                return;
            }

            if (problemUserEntity.getStatus() != ResultEnum.AC) {
                problemUserService.update(pid, uid, resultEnum);
                updateUserTimes(uid, status, resultEnum);
                updateProblemTimes(pid, resultEnum);
            }

        }

        // 保存比赛中的代码提交
        private void saveContestProblem(JudgerTask task, JudgerResult result) {
            int cid = task.getContestId();
            int uid = task.getOwner();
            int pid = task.getProblemId();
            int problemStatus = task.getAddProblemEntity().getStatus();
            int score = 0;
            ResultEnum resultEnum = result.getResponse().getResult();
            ContestEntity contestEntity = task.getAddContestEntity();
            ContestUserEntity contestUserEntity = task.getAddContestUserEntity();
            ContestProblemUserEntity contestProblemUserEntity = contestProblemUserService.get(cid, pid, uid);
            long usedTime = evaluateUsedTime(contestEntity, contestUserEntity);
            if (contestEntity.getType() == 0 || contestEntity.getType() == 1) {
                score = evaluateScore(resultEnum, result.getResponse().getTestCases(),
                        task.getAddTestCaseEntities(), task.getAddContestProblemEntity().getScore());
            }

            if (contestProblemUserEntity == null) {
                if (resultEnum == ResultEnum.AC) {
                    contestProblemUserService.add(cid, pid, uid, score, resultEnum, System.currentTimeMillis(), usedTime);
                } else {
                    contestProblemUserService.add(cid, pid, uid, score, resultEnum, 0, 0);
                }
                // 更新contest_user里面的times
                updateContestUserTimesAndData(cid, uid, score, usedTime, resultEnum);
                // 更新contest_problem
                updateContestProblemTimes(cid, pid, resultEnum);
                // 更新problem times
                updateProblemTimes(pid, resultEnum);
                // 如果比赛为官方official
                // addUserTimes
                if (contestEntity.getOfficial() == 1) {
                    updateUserTimes(uid, problemStatus, resultEnum);
                }
                return;
            }

            if (contestProblemUserEntity.getStatus() != ResultEnum.AC) {
                // 更新contest_problem_user 的成绩状态
                if (resultEnum == ResultEnum.AC) {
                    contestProblemUserService.update(cid, pid, uid, score, resultEnum, System.currentTimeMillis(), usedTime);
                } else {
                    contestProblemUserService.update(cid, pid, uid, score, resultEnum, 0, 0);
                }
                // 更新contest_user里面的times
                updateContestUserTimesAndData(cid, uid, score, usedTime, resultEnum);
                // 更新contest_problem
                updateContestProblemTimes(cid, pid, resultEnum);
                // 更新problem times
                updateProblemTimes(pid, resultEnum);
                // 如果比赛为官方official
                // addUserTimes
                if (contestEntity.getOfficial() == 1) {
                    updateUserTimes(uid, problemStatus, resultEnum);
                }
            }
        }

        private int evaluateScore(ResultEnum result, List<TestCaseResponseEntity> testCases,
                                  List<TestCaseEntity> originTestCases, int totalScore) {
            if (result == ResultEnum.AC) {
                return totalScore;
            }
            List<Integer> strengthList = new ArrayList<>(originTestCases.size());
            double totalPoint = 0;
            for (TestCaseEntity entity: originTestCases) {
                strengthList.add(entity.getStrength());
                totalPoint = totalPoint + entity.getStrength();
            }
            int rightPoint = 0;
            for (int i=0; i<testCases.size(); i++) {
                ResultEnum tempResult = testCases.get(i).getResult();
                if (tempResult == ResultEnum.AC) {
                    rightPoint = rightPoint + strengthList.get(i);
                }
            }
            return (int) Math.floor((totalScore / totalPoint)*rightPoint);
        }

        private void updateUserTimes(int uid, int status, ResultEnum resultEnum) {
            if (status!=2) {
                return;
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setSubmitTimes(1);
            switch (resultEnum) {
                case AC:
                    userEntity.setFinishedProblems(1);
                    userEntity.setACTimes(1);
                    break;
                case CE:
                    userEntity.setCETimes(1);
                    break;
                case WA:
                    userEntity.setWATimes(1);
                    break;
                case RTE:
                    userEntity.setRTETimes(1);
                    break;
                case TLE:
                    userEntity.setTLETimes(1);
                    break;
            }
            userService.updateUserTimes(uid, userEntity);
        }

        private void updateProblemTimes(int pid, ResultEnum resultEnum) {
            ProblemEntity problemEntity = new ProblemEntity();
            problemEntity.setSubmitTimes(1);
            switch (resultEnum) {
                case AC:
                    problemEntity.setACTimes(1);
                    break;
                case TLE:
                    problemEntity.setTLETimes(1);
                    break;
                case RTE:
                    problemEntity.setRTETimes(1);
                    break;
                case WA:
                    problemEntity.setWATimes(1);
                    break;
                case CE:
                    problemEntity.setCETimes(1);
                    break;
            }
            problemService.updateProblemTimes(pid, problemEntity);
        }

        private void updateContestUserTimesAndData(int cid, int uid, int score, long usedTime, ResultEnum resultEnum) {
            ContestUserEntity entity = new ContestUserEntity();
            entity.setCid(cid);
            entity.setUid(uid);
            entity.setScore(score);
            entity.setUsedTime(usedTime);
            entity.setSubmitTimes(1);
            switch (resultEnum) {
                case AC:
                    entity.setFinishedProblems(1);
                    entity.setACTimes(1);
                    break;
                case CE:
                    entity.setCETimes(1);
                    break;
                case WA:
                    entity.setWATimes(1);
                    break;
                case RTE:
                    entity.setRTETimes(1);
                    break;
                case TLE:
                    entity.setTLETimes(1);
                    break;
            }
            contestUserService.updateTimesAndData(cid, uid, entity);
        }

        private void updateContestProblemTimes(int cid, int pid, ResultEnum result) {
            ContestProblemEntity entity = new ContestProblemEntity();
            entity.setSubmitTimes(1);
            switch (result) {
                case AC:
                    entity.setACTimes(1);
                    break;
                case TLE:
                    entity.setTLETimes(1);
                    break;
                case RTE:
                    entity.setRTETimes(1);
                    break;
                case WA:
                    entity.setWATimes(1);
                    break;
                case CE:
                    entity.setCETimes(1);
                    break;
            }
            contestProblemService.updateContestProblemTimes(cid, pid, entity);
        }

        private void saveUserLog(int uid, ResultEnum result) {
            UserLogEntity entity = new UserLogEntity();
            entity.setSubmitTimes(1);
            switch (result) {
                case AC:
                    entity.setACTimes(1);
                    break;
                case TLE:
                    entity.setTLETimes(1);
                    break;
                case RTE:
                    entity.setRTETimes(1);
                    break;
                case WA:
                    entity.setWATimes(1);
                    break;
                case CE:
                    entity.setCETimes(1);
                    break;
            }
            userLogService.updateLog(uid, entity);
        }

        private long evaluateUsedTime(ContestEntity contestEntity, ContestUserEntity contestUserEntity) {
            long startTime = contestEntity.getStartTime();
            long currentTime = System.currentTimeMillis();
            int type = contestEntity.getType();
            if (type == 0 || type == 2) {
                return currentTime-startTime;
            } else {
                return currentTime-contestUserEntity.getJoinTime();
            }
        }
    }
}
