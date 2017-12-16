package org.inlighting.oj.web.judger.runner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.ehcache.Cache;
import org.inlighting.oj.judge.ResultEnum;
import org.inlighting.oj.judge.entity.RequestEntity;
import org.inlighting.oj.judge.entity.ResponseEntity;
import org.inlighting.oj.judge.entity.TestCaseResponseEntity;
import org.inlighting.oj.judge.judger.Judger;
import org.inlighting.oj.judge.judger.judge0.Judge0;
import org.inlighting.oj.web.cache.CacheController;
import org.inlighting.oj.web.entity.*;
import org.inlighting.oj.web.judger.JudgerQueue;
import org.inlighting.oj.web.judger.JudgerResult;
import org.inlighting.oj.web.judger.JudgerStatus;
import org.inlighting.oj.web.judger.JudgerTask;
import org.inlighting.oj.web.service.*;
import org.inlighting.oj.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Smith
 **/
@Component
public class JudgerRunner {

    private final int MAX_THREADS = 3;

    private final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(MAX_THREADS);

    private final Cache<String, JudgerResult> submissionCache = CacheController.getSubmissionCache();

    private SubmissionService submissionService;

    private AttachmentService attachmentService;

    private ProblemUserService problemUserService;

    private ContestProblemService contestProblemService;

    private ContestProblemUserService contestProblemUserService;

    private UserService userService;

    private ProblemService problemService;

    private ContestUserService contestUserService;

    private FileUtil fileUtil;

    @Autowired
    public void setContestProblemService(ContestProblemService contestProblemService) {
        this.contestProblemService = contestProblemService;
    }

    @Autowired
    public void setContestProblemUserService(ContestProblemUserService contestProblemUserService) {
        this.contestProblemUserService = contestProblemUserService;
    }

    @Autowired
    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProblemUserService(ProblemUserService problemUserService) {
        this.problemUserService = problemUserService;
    }

    @Autowired
    public void setContestUserService(ContestUserService contestUserService) {
        this.contestUserService = contestUserService;
    }

    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @Autowired
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Autowired
    public void setSubmissionService(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

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
            ResultEnum resultEnum = result.getResponse().getResult();
            ProblemUserEntity problemUserEntity = problemUserService.get(pid, uid);
            if (problemUserEntity == null) {
                problemUserService.add(pid, uid, resultEnum);
                updateUserTimes(uid, resultEnum);
                updateProblemTimes(pid, resultEnum);
                return;
            }

            if (problemUserEntity.getStatus() != ResultEnum.AC) {
                problemUserService.update(pid, uid, resultEnum);
                updateUserTimes(uid, resultEnum);
                updateProblemTimes(pid, resultEnum);
            }

        }

        // 保存比赛中的代码提交
        private void saveContestProblem(JudgerTask task, JudgerResult result) {
            // todo
            int cid = task.getContestId();
            int uid = task.getOwner();
            int pid = task.getProblemId();
            int score = 0;
            ResultEnum resultEnum = result.getResponse().getResult();
            ContestEntity contestEntity = task.getAddContestEntity();
            ContestProblemUserEntity contestProblemUserEntity = contestProblemUserService.get(cid, pid, uid);

            if (contestEntity.getType() == 0 || contestEntity.getType() == 1) {
                score = evaluateScore(resultEnum, result.getResponse().getTestCases(),
                        task.getAddTestCaseEntities(), task.getAddContestProblemEntity().getScore());
            }

            if (contestProblemUserEntity == null) {
                if (resultEnum == ResultEnum.AC) {
                    contestProblemUserService.add(cid, pid, uid, score, resultEnum, System.currentTimeMillis());
                } else {
                    contestProblemUserService.add(cid, pid, uid, score, resultEnum, 0);
                }
                // 更新contest_user里面的times
                updateContestUserTimes(cid, uid, resultEnum);
                // 更新contest_problem
                updateContestProblemTimes(cid, pid, resultEnum);
                // 更新problem times
                updateProblemTimes(pid, resultEnum);
                // 如果比赛为官方official
                // addUserTimes
                if (contestEntity.getOfficial() == 1) {
                    updateUserTimes(uid, resultEnum);
                }
                return;
            }

            if (contestProblemUserEntity.getStatus() != ResultEnum.AC) {
                // 更新contest_problem_user 的成绩状态
                if (resultEnum == ResultEnum.AC) {
                    contestProblemUserService.update(cid, pid, uid, score, resultEnum, System.currentTimeMillis());
                } else {
                    contestProblemUserService.update(cid, pid, uid, score, resultEnum, 0);
                }
                // 更新contest_user里面的times
                updateContestUserTimes(cid, uid, resultEnum);
                // 更新contest_problem
                updateContestProblemTimes(cid, pid, resultEnum);
                // 更新problem times
                updateProblemTimes(pid, resultEnum);
                // 如果比赛为官方official
                // addUserTimes
                if (contestEntity.getOfficial() == 1) {
                    updateUserTimes(uid, resultEnum);
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

        private void updateUserTimes(int uid, ResultEnum resultEnum) {
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

        private void updateContestUserTimes(int cid, int uid, ResultEnum resultEnum) {
            ContestUserEntity entity = new ContestUserEntity();
            entity.setCid(cid);
            entity.setUid(uid);
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
            contestUserService.updateTimes(cid, uid, entity);
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
    }
}
