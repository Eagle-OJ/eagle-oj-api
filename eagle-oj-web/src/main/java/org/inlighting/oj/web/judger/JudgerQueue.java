package org.inlighting.oj.web.judger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.inlighting.oj.judge.JudgeController;
import org.inlighting.oj.judge.config.CodeLanguageEnum;
import org.inlighting.oj.judge.config.JudgeResponseBean;
import org.inlighting.oj.judge.config.JudgerRequestBean;
import org.inlighting.oj.judge.config.ProblemStatusEnum;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.inlighting.oj.web.service.*;
import org.inlighting.oj.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Smith
 **/
@Component
public class JudgerQueue {

    private Logger LOGGER = LogManager.getLogger(this.getClass());

    private ConcurrentHashMap<String, JudgerTaskResultEntity> resultMapping;

    private PriorityBlockingQueue<JudgerTaskEntity> queue;

    private FileUtil fileUtil;

    private AttachmentService attachmentService;

    private SubmissionService submissionService;

    private ProblemContestInfoService problemContestInfoService;

    private ContestUserInfoService contestUserInfoService;

    private ProblemService problemService;

    private UserService userService;

    private final int DEFAULT_MEMORY_LIMIT = 128;

    private final int DEFAULT_TIME_LIMIT = 3;

    @Value("${eagle-oj.judge.url}")
    private String JUDGE_URL;

    public JudgerQueue() {
        resultMapping = new ConcurrentHashMap<>(10);
        queue = new PriorityBlockingQueue<>(10, ((o1, o2) -> -(o1.getPriority() - o2.getPriority())));
        startRunner();
        resultMappingCleaner();
    }

    @Autowired
    public void setSubmissionService(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Autowired
    public void setProblemContestInfoService(ProblemContestInfoService problemContestInfoService) {
        this.problemContestInfoService = problemContestInfoService;
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
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Autowired
    public void setContestUserInfoService(ContestUserInfoService contestUserInfoService) {
        this.contestUserInfoService = contestUserInfoService;
    }

    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    // 游客提交（测试模式下面）owner=0, contestId=0, groupId=0, score=0
    public String addTask(int priority, int problemId, int owner, int contestId, int score,
                          CodeLanguageEnum codeLanguage, String codeSource,
                          boolean testMode, List<TestCaseEntity> testCaseEntities) {
        String uuid = UUID.randomUUID().toString();
        JudgerTaskEntity judgerTaskEntity = new JudgerTaskEntity(uuid, priority, problemId, codeLanguage, codeSource,
                testMode, testCaseEntities, System.currentTimeMillis());

        JudgerTaskResultEntity judgerTaskResultEntity = new JudgerTaskResultEntity(judgerTaskEntity,
                owner, contestId, score, JudgerTaskStatus.InQueue);

        queue.put(judgerTaskEntity);
        resultMapping.put(uuid, judgerTaskResultEntity);
        return uuid;
    }

    public JudgerTaskResultEntity getResult(String uuid) {
        JudgerTaskResultEntity resultEntity = resultMapping.get(uuid);
        if (resultEntity == null) {
            return null;
        }
        if (resultEntity.getStatus()==JudgerTaskStatus.Finished) {
            resultMapping.remove(uuid);
        } else {
            resultEntity.getJudgerTaskEntity().setTime(System.currentTimeMillis());
        }
        return resultEntity;
    }


    private void startRunner() {
        new Thread(() -> {
            while (true) {
                try {
                    JudgerTaskEntity taskEntity = queue.take();
                    JudgerTaskResultEntity resultEntity = resultMapping.get(taskEntity.getUuid());
                    resultEntity.setStatus(JudgerTaskStatus.Judging);
                    // 判断时间是否超出60s，超出自动废除，并且resultMapping中删除
                    if ((System.currentTimeMillis() - taskEntity.getTime()) > 60000) {
                        resultMapping.remove(taskEntity.getUuid());
                        continue;
                    }

                    // 开始判卷
                    JudgerRequestBean requestBean = new JudgerRequestBean();
                    // 组装stdin[], expectResult[]
                    List<TestCaseEntity> testCaseEntities = taskEntity.getTestCaseEntities();
                    String stdin[] = null;
                    String expectResult[] = null;
                    if (testCaseEntities.size() > 0) {
                        stdin = new String[testCaseEntities.size()];
                        expectResult = new String[testCaseEntities.size()];
                        for (int i=0; i<testCaseEntities.size(); i++) {
                            stdin[i] = testCaseEntities.get(i).getStdin();
                            expectResult[i] = testCaseEntities.get(i).getStdout();
                        }
                    }

                    JudgeResponseBean responseBean = JudgeController.judge(JUDGE_URL, requestBean);
                    requestBean.setCodeLanguage(taskEntity.getCodeLanguage());
                    requestBean.setTestCaseNumber(testCaseEntities.size());
                    requestBean.setExpectResult(expectResult);
                    requestBean.setStdin(stdin);
                    requestBean.setMemoryLimit(DEFAULT_MEMORY_LIMIT);
                    requestBean.setTimeLimit(DEFAULT_TIME_LIMIT);
                    requestBean.setSourceCode(taskEntity.getCodeSource());
                    resultEntity.setJudgeResponseBean(responseBean);
                    // 保存提交记录到problem中
                    saveCommitToProblem(taskEntity.getProblemId(),
                            responseBean.getProblemStatusEnum()== ProblemStatusEnum.Accepted);
                    resultEntity.setStatus(JudgerTaskStatus.Finished);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    private void saveSubmission(JudgerTaskResultEntity resultEntity) {
        int contestId = resultEntity.getContestId();
        int owner = resultEntity.getOwner();
        int problemId = resultEntity.getJudgerTaskEntity().getProblemId();
        int aid = uploadCode(owner, resultEntity.getJudgerTaskEntity().getCodeLanguage(),
                resultEntity.getJudgerTaskEntity().getCodeSource());
        if (aid == 0) {
            resultEntity.setStatus(JudgerTaskStatus.ERROR);
            return;
        }

        int sid = submissionService.submitCode(owner, problemId, aid, resultEntity.getJudgerTaskEntity().getCodeLanguage(),
                contestId, judgeResultLoader(resultEntity.getJudgeResponseBean()),
                resultEntity.getJudgeResponseBean().getRealTime(), resultEntity.getJudgeResponseBean().getMemory(),
                resultEntity.getJudgeResponseBean().getProblemStatusEnum(), resultEntity.getJudgeResponseBean().getDateline());
        if (sid == 0) {
            resultEntity.setStatus(JudgerTaskStatus.ERROR);
            return;
        }

        if (contestId > 0) {
            // todo 完成contestuserinfoservice
            // contestUserInfoService
        }

    }

    private JSONArray judgeResultLoader(JudgeResponseBean stdResponseBean) {
        JSONArray result = new JSONArray();
        for (int i=0; i<stdResponseBean.getTestCaseNumber(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("stderr", stdResponseBean.getStderr()[i]);
            jsonObject.put("status", stdResponseBean.getProblemStatusEnums()[i]);
            result.add(jsonObject);
        }
        return result;
    }

    private void saveCommitToProblem(int pid, boolean isAccept) {
        problemService.addProblemSubmitTimes(pid);
        if (isAccept) {
            problemService.addProblemAcceptTimes(pid);
        }
    }

    private Integer uploadCode(int owner, CodeLanguageEnum languageEnum, String code) {
        String filePath = fileUtil.uploadCode(languageEnum, code);
        return attachmentService.add(owner, filePath, System.currentTimeMillis());
    }

    private void resultMappingCleaner() {
        new Thread(() -> {
            while (true) {
                ArrayList<String> waitToDelete = new ArrayList<>(10);
                resultMapping.forEach((s, judgerTaskResultEntity) -> {
                    if ((System.currentTimeMillis()-judgerTaskResultEntity.getJudgerTaskEntity().getTime())>3*60*1000) {
                        waitToDelete.add(judgerTaskResultEntity.getJudgerTaskEntity().getUuid());
                    }
                });
                for (String uuid: waitToDelete) {
                    resultMapping.remove(uuid);
                }

                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    private void saveSubmission() {

    }
}
