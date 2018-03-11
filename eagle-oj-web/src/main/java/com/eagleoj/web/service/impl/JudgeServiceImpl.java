package com.eagleoj.web.service.impl;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.judge.entity.ResponseEntity;
import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.data.status.ContestTypeStatus;
import com.eagleoj.web.data.status.ProblemStatus;
import com.eagleoj.web.entity.*;
import com.eagleoj.web.file.FileService;
import com.eagleoj.web.judger.JudgeResult;
import com.eagleoj.web.judger.task.ContestJudgeTask;
import com.eagleoj.web.judger.task.GroupJudgeTask;
import com.eagleoj.web.judger.task.ProblemJudgeTask;
import com.eagleoj.web.service.*;
import com.eagleoj.web.setting.SettingService;
import com.eagleoj.web.util.FileUtil;
import com.eagleoj.web.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Smith
 **/
@Service
public class JudgeServiceImpl implements JudgeService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileService fileService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private UserLogService userLogService;

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
    private GroupUserService groupUserService;

    @Autowired
    private SettingService settingService;

    @Override
    public JudgeResult getJudgeResult(String id) {
        JudgeResult result = CacheController.getSubmissionCache().get(id);
        WebUtil.assertNotNull(result, "不存在此次判卷");
        return result;
    }

    @Transactional
    @Override
    public void saveProblemCode(ProblemJudgeTask task, ResponseEntity response) {
        int pid = task.getPid();
        int owner = task.getOwner();
        ResultEnum result = response.getResult();
        saveSubmission(task.getSourceCode(), task.getLang(), response.getTime(), response.getMemory(),
                result, owner, task.getPid(), 0, 0);

        updateUserLog(owner, result);

        boolean isAC = false;
        try {
            ProblemUserEntity problemUserEntity = problemUserService.get(pid, owner);
            if (problemUserEntity.getStatus() == ResultEnum.AC) {
                isAC = true;
            }

            if (problemUserEntity.getStatus() != result) {
                updateProblemUserStatus(owner, pid, result);
            }
        } catch (Exception e) {
            addProblemUserStatus(owner, pid, result);
        }

        if (isAC) {
            return;
        }

        updateProblemTimes(pid, result);
        if (task.getProblemEntity().getStatus() == ProblemStatus.SHARING.getNumber()) {
            updateUserTimes(owner, result);
        }
    }

    @Transactional
    @Override
    public void saveContestCode(ContestJudgeTask task, ResponseEntity response) {
        saveContestCode(task, response, 0);
    }

    @Transactional
    @Override
    public void saveGroupContestCode(GroupJudgeTask task, ResponseEntity response) {
        int gid = task.getGid();
        boolean isAC = saveContestCode(task, response, gid);

        if (isAC) {
            return;
        }
        // 更新组内用户记录
        updateGroupUserTimes(task.getGid(), task.getOwner(), response.getResult());
    }

    // 返回是否已经AC
    private boolean saveContestCode(ContestJudgeTask task, ResponseEntity response, int gid) {
        int pid = task.getPid();
        int cid = task.getCid();
        int owner = task.getOwner();
        ResultEnum result = response.getResult();
        saveSubmission(task.getSourceCode(), task.getLang(), response.getTime(), response.getMemory(),
                result, owner, task.getPid(), cid, gid);

        updateUserLog(owner, result);

        boolean isAC = false;
        long usedTime = evaluateUsedTime(result, task.getContestEntity(), task.getContestUserEntity());
        long solvedTime = (result == ResultEnum.AC ? System.currentTimeMillis(): 0);
        int score = evaluateProblemScore(task.getContestEntity().getType(), result,
                task.getContestProblemEntity().getScore(), task.getTestCases(), response);

        try {
            ContestProblemUserEntity contestProblemUserEntity = contestProblemUserService.getByCidPidUid(cid, pid, owner);
            if (contestProblemUserEntity.getStatus() == ResultEnum.AC) {
                isAC = true;
            }
            if (! isAC) {
                contestProblemUserService.update(cid, pid, owner, score, result, solvedTime, usedTime);
            }
        } catch (Exception e) {
            contestProblemUserService.save(cid, pid, owner, score, result, solvedTime, usedTime);
        }

        if (isAC) {
            return true;
        }

        updateContestProblemTimes(cid, pid, result);
        updateContestUserTimes(cid, owner, usedTime, score, result);
        updateProblemTimes(pid, result);

        if (task.getContestEntity().getOfficial() == 1) {
            updateUserTimes(owner, result);
        }
        return false;
    }

    private void saveSubmission(String sourceCode, LanguageEnum lang, double time, int memory, ResultEnum result,
                                int owner, int pid, int cid, int gid) {
        int aid = 0;
        if (settingService.isOpenStorage()) {
            String uploadURL = null;
            try {
                uploadURL = fileService.uploadCode(lang, sourceCode);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new WebErrorException("保存代码记录失败");
            }
            aid = attachmentService.save(owner, uploadURL);
        }
        submissionService.save(owner, pid, cid, gid, aid, lang, time, memory, result);
    }

    private void addProblemUserStatus(int uid, int pid, ResultEnum result) {
        problemUserService.save(pid, uid, result);
    }

    private void updateProblemUserStatus(int uid, int pid, ResultEnum result) {
        problemUserService.updateByPidUid(pid, uid, result);
    }

    private void updateUserLog(int uid, ResultEnum result) {
        UserLogEntity userLogEntity = new UserLogEntity();
        userLogEntity.setSubmitTimes(1);
        switch (result) {
            case AC:
                userLogEntity.setACTimes(1);
                break;
            case TLE:
                userLogEntity.setTLETimes(1);
                break;
            case RTE:
                userLogEntity.setRTETimes(1);
                break;
            case WA:
                userLogEntity.setWATimes(1);
                break;
            case CE:
                userLogEntity.setCETimes(1);
                break;
        }
        userLogService.save(uid, userLogEntity);
    }

    private void updateUserTimes(int uid, ResultEnum result) {
        UserEntity userEntity = new UserEntity();
        userEntity.setSubmitTimes(1);
        switch (result) {
            case AC:
                userEntity.setACTimes(1);
                userEntity.setFinishedProblems(1);
                break;
            case TLE:
                userEntity.setTLETimes(1);
                break;
            case RTE:
                userEntity.setRTETimes(1);
                break;
            case WA:
                userEntity.setWATimes(1);
                break;
            case CE:
                userEntity.setCETimes(1);
                break;
        }
        userService.updateUser(uid, userEntity);
    }

    private void updateProblemTimes(int pid, ResultEnum result) {
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setSubmitTimes(1);
        switch (result) {
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
        problemService.updateProblem(pid, problemEntity);
    }

    private void updateContestProblemTimes(int cid, int pid, ResultEnum result) {
        ContestProblemEntity contestProblemEntity = new ContestProblemEntity();
        contestProblemEntity.setSubmitTimes(1);
        switch (result) {
            case AC:
                contestProblemEntity.setACTimes(1);
                break;
            case TLE:
                contestProblemEntity.setTLETimes(1);
                break;
            case RTE:
                contestProblemEntity.setRTETimes(1);
                break;
            case WA:
                contestProblemEntity.setWATimes(1);
                break;
            case CE:
                contestProblemEntity.setCETimes(1);
                break;
        }
        contestProblemService.updateContestProblemTimes(cid, pid, contestProblemEntity);
    }

    private void updateContestUserTimes(int cid, int uid, long usedTime, int score, ResultEnum result) {
        ContestUserEntity contestUserEntity = new ContestUserEntity();
        contestUserEntity.setSubmitTimes(1);
        switch (result) {
            case AC:
                contestUserEntity.setTotalUsedTime(usedTime);
                contestUserEntity.setTotalScore(score);
                contestUserEntity.setFinishedProblems(1);
                contestUserEntity.setACTimes(1);
                break;
            case TLE:
                contestUserEntity.setTotalWrongTimes(1);
                contestUserEntity.setTLETimes(1);
                break;
            case RTE:
                contestUserEntity.setTotalWrongTimes(1);
                contestUserEntity.setRTETimes(1);
                break;
            case WA:
                contestUserEntity.setTotalWrongTimes(1);
                contestUserEntity.setWATimes(1);
                break;
            case CE:
                contestUserEntity.setTotalWrongTimes(1);
                contestUserEntity.setCETimes(1);
                break;
        }
        contestUserService.updateByCidUid(cid, uid, contestUserEntity);
    }

    private void updateGroupUserTimes(int gid, int uid, ResultEnum result) {
        GroupUserEntity groupUserEntity = new GroupUserEntity();
        groupUserEntity.setSubmitTimes(1);
        switch (result) {
            case AC:
                groupUserEntity.setFinishedProblems(1);
                groupUserEntity.setACTimes(1);
                break;
            case TLE:
                groupUserEntity.setTLETimes(1);
                break;
            case RTE:
                groupUserEntity.setRTETimes(1);
                break;
            case WA:
                groupUserEntity.setWATimes(1);
                break;
            case CE:
                groupUserEntity.setCETimes(1);
                break;
        }
        groupUserService.updateGroup(gid, uid, groupUserEntity);
    }


    private long evaluateUsedTime(ResultEnum result, ContestEntity contestEntity, ContestUserEntity contestUserEntity) {
        if (result != ResultEnum.AC) {
            return 0;
        }
        int contestType = contestEntity.getType();
        if (contestType == ContestTypeStatus.OI_CONTEST_NORMAL_TIME.getNumber() ||
                contestType == ContestTypeStatus.ACM_CONTEST_NORMAL_TIME.getNumber()) {
            return System.currentTimeMillis() - contestEntity.getStartTime();
        } else {
            return System.currentTimeMillis() - contestUserEntity.getJoinTime();
        }
    }

    private int evaluateProblemScore(int contestType, ResultEnum result, int totalScore, List<TestCaseEntity> testCases,
                                     ResponseEntity response) {
        if (result == ResultEnum.AC) {
            return totalScore;
        }

        if (contestType == ContestTypeStatus.ACM_CONTEST_NORMAL_TIME.getNumber() ||
                contestType == ContestTypeStatus.ACM_CONTEST_LIMIT_TIME.getNumber()) {
            return 0;
        }

        List<Integer> strengthList = new ArrayList<>(testCases.size());
        double totalPoint = 0;
        for (TestCaseEntity entity: testCases) {
            strengthList.add(entity.getStrength());
            totalPoint = totalPoint + entity.getStrength();
        }
        int rightPoint = 0;
        for (int i=0; i<response.getTestCases().size(); i++) {
            ResultEnum tempResult = response.getTestCases().get(i).getResult();
            if (tempResult == ResultEnum.AC) {
                rightPoint = rightPoint + strengthList.get(i);
            }
        }
        return (int) Math.floor((totalScore / totalPoint)*rightPoint);
    }
}
