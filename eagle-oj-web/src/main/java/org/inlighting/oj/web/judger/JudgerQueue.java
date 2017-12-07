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
import org.inlighting.oj.web.entity.ContestProblemUserInfoEntity;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.inlighting.oj.web.service.*;
import org.inlighting.oj.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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

    private ContestProblemService problemContestInfoService;

    private ContestProblemUserInfoService contestProblemUserInfoService;

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
    public void setProblemContestInfoService(ContestProblemService problemContestInfoService) {
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
    public void setContestProblemUserInfoService(ContestProblemUserInfoService contestProblemUserInfoService) {
        this.contestProblemUserInfoService = contestProblemUserInfoService;
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
    public String addTask(int priority, int problemId, int owner, int contestId, int contestType, int score,
                          CodeLanguageEnum codeLanguage, String codeSource,
                          boolean testMode, List<TestCaseEntity> testCaseEntities) {
        String uuid = UUID.randomUUID().toString();
        JudgerTaskEntity judgerTaskEntity = new JudgerTaskEntity(uuid, priority, problemId, codeLanguage, codeSource,
                testMode, testCaseEntities, System.currentTimeMillis());

        JudgerTaskResultEntity judgerTaskResultEntity = new JudgerTaskResultEntity(judgerTaskEntity,
                owner, contestId, contestType, score, JudgerTaskStatus.InQueue);

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
                    } else {
                        resultEntity.setStatus(JudgerTaskStatus.ERROR);
                        break;
                    }

                    requestBean.setCodeLanguage(taskEntity.getCodeLanguage());
                    requestBean.setTestCaseNumber(testCaseEntities.size());
                    requestBean.setExpectResult(expectResult);
                    requestBean.setStdin(stdin);
                    requestBean.setMemoryLimit(DEFAULT_MEMORY_LIMIT);
                    requestBean.setTimeLimit(DEFAULT_TIME_LIMIT);
                    requestBean.setSourceCode(taskEntity.getCodeSource());

                    JudgeResponseBean responseBean = JudgeController.judge(JUDGE_URL, requestBean);
                    resultEntity.setJudgeResponseBean(responseBean);

                    // 非测试模式开始保存记录
                    if (! resultEntity.getJudgerTaskEntity().isTestMode()) {
                        saveSubmission(resultEntity);
                    }
                    resultEntity.setStatus(JudgerTaskStatus.Finished);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    private void saveSubmission(JudgerTaskResultEntity resultEntity) {
        resultEntity.setStatus(JudgerTaskStatus.Saving);

        boolean isAccepted = resultEntity.getJudgeResponseBean().getProblemStatusEnum() == ProblemStatusEnum.Accepted;
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

        // 更新problem的记录
        saveSubmitToProblem(problemId, isAccepted);

        // 更新user用户的信息
        saveSubmitToUser(owner, isAccepted);

        if (contestId > 0) {
            // 保存用户与题目之间的关联
            // 首先检测用户是否做过此题
            ContestProblemUserInfoEntity contestProblemUserInfoEntity = contestProblemUserInfoService.get(contestId, problemId,
                    owner);
            boolean hadSolved = contestProblemUserInfoEntity != null;
            boolean isSolved = false;
            if (hadSolved) {
                isSolved  = contestProblemUserInfoEntity.getStatus() == ProblemStatusEnum.Accepted;
            }
            // 题目提交做通过了不会保存信息
            if (isSolved) {
                return;
            }

            // 保存比赛中题目的信息
            saveContestProblemInfo(contestId, problemId, isAccepted);

            // 保存比赛成绩
            saveContestScore(contestId, owner, problemId ,resultEntity.getContestType(), isAccepted, hadSolved,
                    resultEntity);
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

    private void saveContestScore(int cid, int uid, int pid ,int contestType, boolean isAccepted, boolean hadSolved,
                                  JudgerTaskResultEntity resultEntity) {
        int score = 0;
        long newlyAcceptTime = 0;
        int submitTimes = 1;
        int acceptTimes = 0;
        List<TestCaseEntity> testCaseEntities = resultEntity.getJudgerTaskEntity().getTestCaseEntities();
        ProblemStatusEnum[] problemStatuses = resultEntity.getJudgeResponseBean().getProblemStatusEnums();
        if (contestType==2 || contestType==3) {
            // ACM模式，全对才加分
            if (isAccepted) {
                score = resultEntity.getScore();
                newlyAcceptTime = System.currentTimeMillis();
            }
        } else {
            score = computeScore(testCaseEntities, problemStatuses, resultEntity.getScore());
            resultEntity.setScore(score);
        }

        if (isAccepted) {
            acceptTimes = 1;
        }
        // 如果以前没做过，保存用户在比赛题目之间的关系
        if (hadSolved) {
            contestUserInfoService.updateData(cid, uid, submitTimes, acceptTimes, newlyAcceptTime);
            contestProblemUserInfoService.updateStatusScore(cid, pid, uid, score, resultEntity.getJudgeResponseBean().getProblemStatusEnum());
        } else {
            contestProblemUserInfoService.add(cid, pid, uid, score, resultEntity.getJudgeResponseBean().getProblemStatusEnum());
        }
    }

    private int computeScore(List<TestCaseEntity> testCaseEntities, ProblemStatusEnum[] problemStatusEnums, int score) {
        double totalStrength = 0;
        double result = 0;
        boolean hadFault = false;
        double[] strengths = new double[testCaseEntities.size()];
        for (int i=0; i<testCaseEntities.size(); i++) {
            int strength = testCaseEntities.get(i).getStrength();;
            totalStrength = totalStrength + strength;
            strengths[i] = strength;
        }
        double preStrength = score/totalStrength;
        for (int i=0; i<problemStatusEnums.length; i++) {
            if (problemStatusEnums[i] == ProblemStatusEnum.Accepted) {
                result = result + strengths[i] * preStrength;
            } else {
                hadFault = true;
            }
        }

        if (!hadFault) {
            return score;
        } else {
            return (int) Math.floor(result);
        }
    }

    private void saveContestProblemInfo(int cid, int pid, boolean isAccepted) {
        problemContestInfoService.addSubmitTimes(pid, cid);
        if (isAccepted) {
            problemContestInfoService.addAcceptTimes(pid, cid);
        }
    }

    private void saveSubmitToProblem(int pid, boolean isAccept) {
        problemService.addProblemSubmitTimes(pid);
        if (isAccept) {
            problemService.addProblemAcceptTimes(pid);
        }
    }

    private void saveSubmitToUser(int uid, boolean isAccept) {
        userService.addUserSubmitTimes(uid);
        if (isAccept) {
            userService.addUserAcceptTimes(uid);
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

}
