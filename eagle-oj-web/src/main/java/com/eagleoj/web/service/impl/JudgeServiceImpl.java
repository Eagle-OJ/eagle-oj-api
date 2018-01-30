package com.eagleoj.web.service.impl;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.judge.entity.ResponseEntity;
import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.data.status.ProblemStatus;
import com.eagleoj.web.entity.ProblemEntity;
import com.eagleoj.web.entity.ProblemUserEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.entity.UserLogEntity;
import com.eagleoj.web.judger.JudgeResult;
import com.eagleoj.web.judger.task.ProblemJudgeTask;
import com.eagleoj.web.service.*;
import com.eagleoj.web.util.FileUtil;
import com.eagleoj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Smith
 **/
@Service
public class JudgeServiceImpl implements JudgeService {

    @Autowired
    private FileUtil fileUtil;

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
        boolean isNeedUpdate = false;
        try {
            ProblemUserEntity problemUserEntity = problemUserService.get(pid, owner);
            if (problemUserEntity.getStatus() == ResultEnum.AC) {
                isAC = true;
            }

            if (problemUserEntity.getStatus() != result) {
                isNeedUpdate = true;
            }
        } catch (Exception e) {
            addProblemUserStatus(owner, pid, result);
        }

        if (isAC) {
            return;
        }

        if (isNeedUpdate) {
            updateProblemUserStatus(owner, pid, result);
        }
        updateProblemTimes(pid, result);
        if (task.getProblemEntity().getStatus() == ProblemStatus.SHARING.getNumber()) {
            updateUserTimes(owner, result);
        }
    }

    @Transactional
    @Override
    public void saveContestCode(ProblemJudgeTask task, ResponseEntity response) {

    }

    @Transactional
    @Override
    public void saveGroupContestCode(ProblemJudgeTask task, ResponseEntity response) {

    }

    private void saveSubmission(String sourceCode, LanguageEnum lang, double time, int memory, ResultEnum result,
                                int owner, int pid, int cid, int gid) {
        String uploadURL = fileUtil.uploadCode(lang, sourceCode);
        int aid = attachmentService.save(owner, uploadURL);
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
}
