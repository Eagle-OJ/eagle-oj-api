package org.inlighting.oj.web.controller.user;

import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.controller.format.user.SubmitCodeFormat;
import org.inlighting.oj.web.entity.*;
import org.inlighting.oj.web.judger.JudgerQueue;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/user/submission", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserSubmissionController {

    private ContestService contestService;

    private ContestUserInfoService contestUserInfoService;

    private TestCasesService testCaseService;

    private ContestProblemService problemContestInfoService;

    private JudgerQueue judgerQueue;

    @Autowired
    public void setProblemContestInfoService(ContestProblemService problemInfoService) {
        this.problemContestInfoService = problemInfoService;
    }

    @Autowired
    public void setTestCaseService(TestCasesService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @Autowired
    public void setContestUserInfoService(ContestUserInfoService contestUserInfoService) {
        this.contestUserInfoService = contestUserInfoService;
    }

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    @PostMapping
    public ResponseEntity submitCode(@RequestBody @Valid SubmitCodeFormat format) {
        int uid = SessionHelper.get().getUid();
        int contestId = format.getContestId();
        int contestType = 0;
        int problemId = format.getProblemId();
        int score = 0;

        // 首先检查problem的test_cases，不存在则不能做
        List<TestCaseEntity> testCaseEntities = testCaseService.getAllTestCasesByPid(problemId);
        if (testCaseEntities.size() == 0) {
            throw new WebErrorException("此题无测试数据");
        }

        if (contestId > 0) {
            ContestUserInfoEntity contestUserInfoEntity = contestUserInfoService.getByCidAndUid(contestId, uid);
            if (contestUserInfoEntity == null) {
                throw new WebErrorException("你不在比赛中");
            }

            ContestEntity contestEntity = contestService.getContestByCid(contestId);
            contestType = contestEntity.getType();
            if (contestEntity.getType() == 1 || contestEntity.getType() ==3) {
                if ((contestUserInfoEntity.getJoinTime()+contestEntity.getTotalTime()) > System.currentTimeMillis()) {
                    throw new WebErrorException("比赛时间超时");
                }
            }

            ContestProblemEntity problemContestInfoEntity = problemContestInfoService.getContestProblem(problemId, contestId);
            if (problemContestInfoEntity == null) {
                throw new WebErrorException("问题不在比赛中");
            }
            score = problemContestInfoEntity.getScore();
        }

        String uuid = judgerQueue.addTask(1, problemId, uid, contestId, contestType ,score, format.getCodeLanguage(),
                format.getCodeSource(), false, testCaseEntities);

        return new ResponseEntity("提交成功", uuid);
    }

    /*@ApiOperation("提交指定题目的的答案-非比赛中")
    @PostMapping("/problem/{pid}")
    public ResponseEntity submitProblemAnswerInGlobal(@PathVariable int pid,
                                              @RequestBody @Valid SubmitProblemAnswerFormat format) throws Exception {
        // todo 获得这道题在数据库里面的总分
        int uid = SessionHelper.get().getUid();
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        if (problemEntity == null) {
            throw new WebErrorException("题目不存在");
        }

        if (problemEntity.getShare()!=2) {
            throw new WebErrorException("你无权提交此题");
        }

        Future<StdResponseBean> responseBeanFuture = judger.getResult(requestBeanLoader(pid, format));
        Future<Integer> aidFuture = judger.uploadCode(uid, format.getLanguageEnum(), format.getCode());

        StdResponseBean stdResponseBean = responseBeanFuture.get();
        int aid = aidFuture.get();
        if (aid == 0) {
            throw new WebErrorException("代码提交失败");
        }


        if (! submissionService.submitCode(uid, pid,
                aid, format.getLanguageEnum(), 0,
                judgeResultLoader(stdResponseBean), stdResponseBean.getRealTime(), stdResponseBean.getMemory(),
                stdResponseBean.getProblemStatusEnum(), stdResponseBean.getDateline())) {
            throw new WebErrorException("代码提交失败");
        }

        // 添加用户的提交记录
        userService.addUserSubmitTimes(uid);
        // 答案正确，添加acceptTimes
        if (stdResponseBean.getProblemStatusEnum() == ProblemStatusEnum.Accepted) {
            userService.addUserAcceptTimes(uid);
        }

        // 添加全局 problem_info 的submitTimes和AcceptTimes
        problemInfoService.addSubmitTimes(pid, 0);
        if (stdResponseBean.getProblemStatusEnum() == ProblemStatusEnum.Accepted) {
            problemInfoService.addAcceptTimes(pid, 0);
        }
        return new ResponseEntity("代码提交成功", stdResponseBean);
    }

    @ApiOperation("提交在比赛中的答案")
    @PostMapping("/contest/{contest}/problem/{pid}}")
    public ResponseEntity submitProblemAnswerInContest(@PathVariable int cid,
                                                       @PathVariable int pid,
                                                       @RequestBody @Valid SubmitProblemAnswerFormat format) throws Exception {
        int uid = SessionHelper.get().getUid();

        ContestEntity contestEntity = contestService.getContestByCid(cid, true);
        if (contestEntity == null) {
            throw new WebErrorException("比赛不存在");
        }

        if (contestEntity.getStatus() == 0) {
            throw new WebErrorException("比赛已经结束");
        }

        // 检验用户和比赛的关系
        ContestUserInfoEntity contestUserInfoEntity = contestUserInfoService.getByCidAndUid(contestEntity.getCid(), uid);
        if (contestUserInfoEntity == null) {
            throw new WebErrorException("你没有加入比赛");
        }

        // 如果是限时比赛，检验是否超时。
        if (contestEntity.getType() == 1 || contestEntity.getType() == 3) {
            if ((contestUserInfoEntity.getJoinTime()+contestEntity.getTotalTime()) > System.currentTimeMillis()) {
                throw new WebErrorException("你已经没有多余的时间了");
            }
        }

        // 检验题目和比赛的关系
        ProblemInfoEntity problemInfoEntity = problemInfoService.getByPidAndBelong(pid, cid);
        if (problemInfoEntity == null) {
            throw new WebErrorException("比赛与题目关系非法");
        }

        Future<StdResponseBean> responseBeanFuture = judger.getResult(requestBeanLoader(pid, format));
        Future<Integer> aidFuture = judger.uploadCode(uid, format.getLanguageEnum(), format.getCode());

        StdResponseBean stdResponseBean = responseBeanFuture.get();
        int aid = aidFuture.get();
        if (aid == 0) {
            throw new WebErrorException("代码提交失败");
        }

        if (! submissionService.submitCode(uid, pid, aid, format.getLanguageEnum(), cid,
                judgeResultLoader(stdResponseBean), stdResponseBean.getRealTime(), stdResponseBean.getMemory(),
                stdResponseBean.getProblemStatusEnum(), stdResponseBean.getDateline())) {
            throw new WebErrorException("代码提交失败");
        }

        if (contestEntity.getOfficial() == 1) {

        }
        return null;
    }*/

    /*private StdRequestBean requestBeanLoader(int pid, SubmitProblemAnswerFormat format) {
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
        return stdRequestBean;
    }*/


}
