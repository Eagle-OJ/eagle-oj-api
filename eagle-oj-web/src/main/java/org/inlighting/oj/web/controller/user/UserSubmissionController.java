package org.inlighting.oj.web.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.judge.bean.StdRequestBean;
import org.inlighting.oj.judge.bean.StdResponseBean;
import org.inlighting.oj.web.controller.format.user.SubmitProblemAnswerFormat;
import org.inlighting.oj.web.entity.*;
import org.inlighting.oj.web.judger.Judger;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/user/submission", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserSubmissionController {

    private ProblemService problemService;

    private ContestService contestService;

    private ContestUserInfoService contestUserInfoService;

    private TestCaseService testCaseService;

    private SubmissionService submissionService;

    private Judger judger;

    @Autowired
    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Autowired
    public void setTestCaseService(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @Autowired
    public void setJudger(Judger judger) {
        this.judger = judger;
    }

    @Autowired
    public void setContestUserInfoService(ContestUserInfoService contestUserInfoService) {
        this.contestUserInfoService = contestUserInfoService;
    }

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    @Autowired
    public void setSubmissionService(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @ApiOperation("提交指定题目的的答案-非比赛中")
    @PostMapping("/problem/{pid}")
    public ResponseEntity submitProblemAnswerInGlobal(@PathVariable int pid,
                                              @RequestBody @Valid SubmitProblemAnswerFormat format) throws Exception {
        // todo 获得这道题在数据库里面的总分
        int uid = SessionHelper.get().getUid();
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        if (problemEntity == null) {
            throw new RuntimeException("题目不存在");
        }

        if (problemEntity.getShare()!=2) {
            throw new RuntimeException("你无权提交此题");
        }

        List<TestCaseEntity> testCaseEntities = testCaseService.getAllTestCasesByPid(pid);

        String[] stdin = new String[testCaseEntities.size()];
        String[] expectResult = new String[testCaseEntities.size()];
        for (int i=0; i<testCaseEntities.size(); i++) {
            stdin[i] = testCaseEntities.get(i).getStdin();
            expectResult[i] = testCaseEntities.get(i).getStdout();
        }


        StdRequestBean stdRequestBean = new StdRequestBean();
        stdRequestBean.setTestCaseNumber(testCaseEntities.size());
        stdRequestBean.setLanguage(format.getLanguageEnum());
        stdRequestBean.setSourceCode(format.getCode());
        stdRequestBean.setStdin(stdin);
        stdRequestBean.setExpectResult(expectResult);
        stdRequestBean.setTimeLimit(3);
        stdRequestBean.setMemoryLimit(128);

        Future<StdResponseBean> responseBeanFuture = judger.getResult(stdRequestBean);
        Future<Integer> aidFuture = judger.uploadCode(uid, format.getLanguageEnum(), format.getCode());


        StdResponseBean stdResponseBean = responseBeanFuture.get();
        int aid = aidFuture.get();
        if (aid == 0) {
            throw new RuntimeException("代码提交失败");
        }
        JSONArray jsonArray = new JSONArray();
        for (int i=0; i<stdResponseBean.getTestCaseNumber(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("stderr", stdResponseBean.getStderr()[i]);
            jsonObject.put("status", stdResponseBean.getProblemStatusEnums()[i]);
            jsonArray.add(jsonObject);
        }

        StdResponseBean responseBean = responseBeanFuture.get();
        if (! submissionService.submitCodeInGlobal(true, uid, problemEntity.getPid(),
                aid, format.getLanguageEnum(), 0,
                jsonArray, responseBean.getRealTime(), responseBean.getMemory(),
                responseBean.getProblemStatusEnum(), responseBean.getDateline())) {
            throw new RuntimeException("代码提交失败");
        }
        return new ResponseEntity("代码提交成功", stdResponseBean);
    }

    @ApiOperation("提交在比赛中的答案")
    @PostMapping("/contest/{contest}/problem/{pid}}")
    public ResponseEntity submitProblemAnswerInContest(@PathVariable int cid,
                                                       @PathVariable int pid,
                                                       @RequestBody @Valid SubmitProblemAnswerFormat format) throws Exception {
        int uid = SessionHelper.get().getUid();

        ContestEntity contestEntity = contestService.getContestByCid(cid);
        if (contestEntity == null) {
            throw new RuntimeException("比赛不存在");
        }

        ContestUserInfoEntity contestUserInfoEntity = contestUserInfoService.getByCidAndUid(contestEntity.getCid(), uid);
        
        return null;
    }
}
