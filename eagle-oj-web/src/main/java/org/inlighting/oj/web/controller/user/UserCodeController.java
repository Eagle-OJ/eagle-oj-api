package org.inlighting.oj.web.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.judge.JudgeHelper;
import org.inlighting.oj.judge.LanguageEnum;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private SubmissionService submissionService;

    private JudgerManager judgerManager;

    @Autowired
    public void setSubmissionService(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

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

    @ApiOperation("用户提交代码进行判卷")
    @PostMapping
    public ResponseEntity submitCode(@RequestBody @Valid SubmitCodeFormat format) {
        Integer contestId = format.getContestId();
        if (contestId == 0) {
            return submitProblem(format);
        } else {
            return submitContestProblem(format);
        }
    }

    @ApiOperation("获取用户的代码提交")
    @GetMapping
    public ResponseEntity getUserSubmissions(@RequestParam("cid") int cid,
                                             @RequestParam("pid") int pid,
                                             @RequestParam("page") int page,
                                             @RequestParam("page_size") int pageSize) {
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        int owner = SessionHelper.get().getUid();
        Map<String, Object> data = new HashMap<>(2);
        data.put("data", submissionService.getSubmissions(owner, cid, pid, pager));
        data.put("total", pager.getTotal());
        return new ResponseEntity(data);
    }

    // 提交比赛模式下面的题目
    private ResponseEntity submitContestProblem(SubmitCodeFormat format) {
        int contestId = format.getContestId();
        int problemId = format.getProblemId();
        int uid = SessionHelper.get().getUid();

        ContestEntity contestEntity = contestService.getContestByCid(contestId);
        WebUtil.assertNotNull(contestEntity, "比赛不存在");;

        // 检验比赛状态是否合法
        if (contestEntity.getStatus()!=1) {
            throw new WebErrorException("比赛不得参加");
        }

        // 获取用户是否参加比赛
        ContestUserEntity contestUserEntity = contestUserService.get(contestId, uid);
        WebUtil.assertNotNull(contestUserEntity, "用户没有参加此比赛");

        // 查询如果为时间限制模式，用户是否能提交判卷
        if (contestEntity.getType() == 1 || contestEntity.getType() == 3) {
            if (System.currentTimeMillis() > (contestUserEntity.getJoinTime()+contestEntity.getTotalTime())) {
                throw new WebErrorException("你已超时，不能提交");
            }
        }

        ProblemEntity problemEntity = problemService.getProblemByPid(problemId);
        WebUtil.assertNotNull(problemEntity, "题目不存在");

        // 获取题目是否在次比赛中
        ContestProblemEntity contestProblemEntity = contestProblemService.getContestProblem(problemId, contestId);
        WebUtil.assertNotNull(contestProblemEntity, "题目不在比赛中");

        containLang(format.getLang(), problemEntity.getLang());

        // 组装testCases
        List<TestCaseEntity> tempTestCases = testCaseService.getAllTestCasesByPid(problemId);
        if (tempTestCases.size()==0) {
            throw new WebErrorException("此题没有测试用例");
        }

        List<TestCaseRequestEntity> testCases = new ArrayList<>(tempTestCases.size());
        for(TestCaseEntity entity: tempTestCases) {
            testCases.add(new TestCaseRequestEntity(entity.getStdin(), entity.getStdout()));
        }

        String id = judgerManager.addTask(false, problemId, contestId, uid, format.getLang(),
                format.getSourceCode(), problemEntity.getTime(),
                problemEntity.getMemory(), testCases, tempTestCases,
                problemEntity, contestEntity, contestProblemEntity, contestUserEntity);
        return new ResponseEntity(null, id);
    }

    // 提交非比赛模式下面的代码
    private ResponseEntity submitProblem(SubmitCodeFormat format) {
        int problemId = format.getProblemId();
        int uid = SessionHelper.get().getUid();
        ProblemEntity problemEntity = problemService.getProblemByPid(problemId);

        WebUtil.assertNotNull(problemEntity, "不存在此题");
        containLang(format.getLang(), problemEntity.getLang());

        if (problemEntity.getStatus() != 2) {
            throw new WebErrorException("此题不能提交");
        }
        List<TestCaseEntity> tempTestCases = testCaseService.getAllTestCasesByPid(problemId);
        List<TestCaseRequestEntity> testCases = new ArrayList<>(tempTestCases.size());
        for(TestCaseEntity entity: tempTestCases) {
            testCases.add(new TestCaseRequestEntity(entity.getStdin(), entity.getStdout()));
        }
        String id = judgerManager.addTask(false, problemId, 0, uid, format.getLang(),
                format.getSourceCode(), problemEntity.getTime(), problemEntity.getMemory(),
                testCases, tempTestCases, problemEntity,
                null, null, null);
        return new ResponseEntity(null, id);
    }

    private void containLang(LanguageEnum lang, JSONArray langArray) {
        for (Object obj: langArray) {
            if (lang == LanguageEnum.valueOf(obj.toString())) {
                return;
            }
        }
        throw new WebErrorException("不能使用此语言");
    }
}
