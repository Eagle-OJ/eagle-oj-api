package org.inlighting.oj.web.controller.user;

import org.inlighting.oj.judge.entity.TestCaseRequestEntity;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.controller.format.user.SubmitCodeFormat;
import org.inlighting.oj.web.entity.*;
import org.inlighting.oj.web.judger.JudgerManager;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.*;
import org.inlighting.oj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/user/code", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserCodeController {

    private ContestService contestService;

    private ContestUserService contestUserService;

    private TestCasesService testCaseService;

    private ContestProblemService contestProblemService;

    private ProblemService problemService;

    private JudgerManager judgerManager;

    @Autowired
    public void setJudgerManager(JudgerManager judgerManager) {
        this.judgerManager = judgerManager;
    }

    @Autowired
    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Autowired
    public void setContestProblemService(ContestProblemService contestProblemService) {
        this.contestProblemService = contestProblemService;
    }

    @Autowired
    public void setTestCaseService(TestCasesService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @Autowired
    public void setContestUserService(ContestUserService contestUserService) {
        this.contestUserService = contestUserService;
    }

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    @PostMapping
    public ResponseEntity submitCode(@RequestBody @Valid SubmitCodeFormat format) {
        Integer contestId = format.getContestId();
        if (contestId == null) {
            return submitProblem(format);
        } else {
            return submitContestProblem(format);
        }
    }

    // 提交比赛模式下面的题目
    private ResponseEntity submitContestProblem(SubmitCodeFormat format) {
        int contestId = format.getContestId();
        int problemId = format.getProblemId();
        int uid = SessionHelper.get().getUid();

        ProblemEntity problemEntity = problemService.getProblemByPid(problemId);
        WebUtil.assertNotNull(problemEntity, "题目不存在");

        // 组装testCases
        List<TestCaseEntity> tempTestCases = testCaseService.getAllTestCasesByPid(problemId);
        if (tempTestCases.size()==0) {
            throw new WebErrorException("此题没有测试用例");
        }

        List<TestCaseRequestEntity> testCases = new ArrayList<>(tempTestCases.size());
        for(TestCaseEntity entity: tempTestCases) {
            testCases.add(new TestCaseRequestEntity(entity.getStdin(), entity.getStdout()));
        }

        ContestEntity contestEntity = contestService.getContestByCid(contestId);
        WebUtil.assertNotNull(contestEntity, "比赛不存在");;

        if (contestEntity.getStatus()!=1) {
            throw new WebErrorException("比赛不得参加");
        }

        // 获取用户是否参加比赛
        ContestUserEntity contestUserEntity = contestUserService.getByCidAndUid(contestId, uid);
        WebUtil.assertNotNull(contestUserEntity, "用户没有参加此比赛");

        // 查询如果为时间限制模式，用户是否能提交判卷
        if (contestEntity.getStatus() == 1 || contestEntity.getStatus() == 3) {
            if (System.currentTimeMillis() > (contestUserEntity.getJoinTime()+contestEntity.getTotalTime())) {
                throw new WebErrorException("你已超时，不能判卷");
            }
        }

        // 获取题目是否在次比赛中
        ContestProblemEntity contestProblemEntity = contestProblemService.getContestProblem(problemId, contestId);
        WebUtil.assertNotNull(contestProblemEntity, "题目不在比赛中");

        String id = judgerManager.addTask(false, problemId, contestId, uid, format.getLang(),
                format.getSourceCode(), testCases, tempTestCases, problemEntity, contestEntity, contestProblemEntity);
        return new ResponseEntity(id);
    }

    // 提交非比赛模式下面的代码
    private ResponseEntity submitProblem(SubmitCodeFormat format) {
        int problemId = format.getProblemId();
        int uid = SessionHelper.get().getUid();
        ProblemEntity problemEntity = problemService.getProblemByPid(problemId);
        if (problemEntity.getStatus() != 2) {
            throw new WebErrorException("此题不能提交");
        }
        List<TestCaseEntity> tempTestCases = testCaseService.getAllTestCasesByPid(problemId);
        List<TestCaseRequestEntity> testCases = new ArrayList<>(tempTestCases.size());
        for(TestCaseEntity entity: tempTestCases) {
            testCases.add(new TestCaseRequestEntity(entity.getStdin(), entity.getStdout()));
        }
        String id = judgerManager.addTask(false, problemId, 0, uid, format.getLang(),
                format.getSourceCode(), testCases, tempTestCases, problemEntity, null, null);
        return new ResponseEntity(id);
    }
}
