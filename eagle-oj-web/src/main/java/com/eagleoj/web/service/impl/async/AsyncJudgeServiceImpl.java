package com.eagleoj.web.service.impl.async;

import com.alibaba.fastjson.JSONArray;
import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.entity.TestCaseRequestEntity;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.data.status.ContestStatus;
import com.eagleoj.web.data.status.ContestTypeStatus;
import com.eagleoj.web.data.status.ProblemStatus;
import com.eagleoj.web.entity.*;
import com.eagleoj.web.judger.JudgeQueue;
import com.eagleoj.web.judger.task.ContestJudgeTask;
import com.eagleoj.web.judger.task.GroupJudgeTask;
import com.eagleoj.web.judger.task.ProblemJudgeTask;
import com.eagleoj.web.judger.task.TestJudgeTask;
import com.eagleoj.web.service.*;
import com.eagleoj.web.service.async.AsyncJudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Smith
 **/
@Service
public class AsyncJudgeServiceImpl implements AsyncJudgeService {

    @Autowired
    private JudgeQueue judgerQueue;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private TestCasesService testCasesService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private ContestUserService contestUserService;

    @Autowired
    private ContestProblemService contestProblemService;

    @Autowired
    private GroupUserService groupUserService;

    @Override
    public String addTestJudge(String sourceCode, LanguageEnum lang, int time, int memory, List<TestCaseEntity> testCases) {
        TestJudgeTask task = new TestJudgeTask();
        task.setId(generateId());
        task.setSourceCode(sourceCode);
        task.setLang(lang);
        task.setTime(time);
        task.setMemory(memory);
        task.setTestCases(testCases);
        task.setPriority(0);
        judgerQueue.addTask(task);
        return task.getId();
    }

    @Override
    public String addProblemJudge(String sourceCode, LanguageEnum lang,
                                  int owner, int pid) {
        ProblemEntity problemEntity = problemService.getProblem(pid);
        containLang(lang, problemEntity);
        ProblemJudgeTask task = new ProblemJudgeTask();
        task.setId(generateId());
        task.setSourceCode(sourceCode);
        task.setLang(lang);
        task.setTime(problemEntity.getTime());
        task.setMemory(problemEntity.getMemory());
        task.setTestCases(getTestCases(pid));
        task.setOwner(owner);
        task.setPid(pid);
        task.setProblemEntity(problemEntity);
        task.setPriority(1);
        judgerQueue.addTask(task);
        return task.getId();
    }

    @Override
    public String addContestJudge(String sourceCode, LanguageEnum lang,
                                  int owner, int pid,
                                  int cid) {
        ProblemEntity problemEntity = problemService.getProblem(pid);
        containLang(lang, problemEntity);
        ContestEntity contestEntity = contestService.getContest(cid);
        // 检查比赛是否可以进行
        checkContestStatus(contestEntity);
        // 查看用户是否已经加入比赛
        ContestUserEntity contestUserEntity = contestUserService.get(cid, owner);
        // 检验用户是否在比赛中还能提交题目
        checkUserInContestValidation(contestEntity, contestUserEntity);
        // 检验题目是否在本次比赛中
        ContestProblemEntity contestProblemEntity = contestProblemService.getContestProblem(cid, pid);

        ContestJudgeTask task = new ContestJudgeTask();
        task.setId(generateId());
        task.setSourceCode(sourceCode);
        task.setLang(lang);
        task.setTime(problemEntity.getTime());
        task.setMemory(problemEntity.getMemory());
        task.setTestCases(getTestCases(pid));
        task.setOwner(owner);
        task.setPid(pid);
        task.setProblemEntity(problemEntity);
        task.setPriority(1);
        task.setCid(cid);
        task.setContestEntity(contestEntity);
        task.setContestProblemEntity(contestProblemEntity);
        task.setContestUserEntity(contestUserEntity);
        judgerQueue.addTask(task);
        return task.getId();
    }

    @Override
    public String addGroupJudge(String sourceCode, LanguageEnum lang,
                                int owner, int pid,
                                int cid,
                                int gid) {
        ProblemEntity problemEntity = problemService.getProblem(pid);
        containLang(lang, problemEntity);
        ContestEntity contestEntity = contestService.getContest(cid);
        // 检查比赛是否可以进行
        checkContestStatus(contestEntity);
        // 查看用户是否已经加入比赛
        ContestUserEntity contestUserEntity = contestUserService.get(cid, owner);
        // 检验用户是否在比赛中还能提交题目
        checkUserInContestValidation(contestEntity, contestUserEntity);
        // 检验题目是否在本次比赛中
        ContestProblemEntity contestProblemEntity = contestProblemService.getContestProblem(cid, pid);

        // 检查是否为小组比赛
        int tempGid = contestEntity.getGroup();
        if (tempGid != gid) {
            throw new WebErrorException("本场比赛不属于该小组");
        }
        // 检查用户是否在小组中
        GroupUserEntity groupUserEntity = groupUserService.getGroupMember(gid, owner);

        GroupJudgeTask task = new GroupJudgeTask();
        task.setId(generateId());
        task.setSourceCode(sourceCode);
        task.setLang(lang);
        task.setTime(problemEntity.getTime());
        task.setMemory(problemEntity.getMemory());
        task.setTestCases(getTestCases(pid));
        task.setOwner(owner);
        task.setPid(pid);
        task.setProblemEntity(problemEntity);
        task.setPriority(1);
        task.setCid(cid);
        task.setContestEntity(contestEntity);
        task.setContestProblemEntity(contestProblemEntity);
        task.setContestUserEntity(contestUserEntity);
        task.setGid(gid);
        judgerQueue.addTask(task);
        return task.getId();
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }


    private List<TestCaseEntity> getTestCases(int pid) {
        List<TestCaseEntity> testCases = testCasesService.listProblemTestCases(pid);
        if (testCases.size() == 0) {
            throw new WebErrorException("该题没有测试用例，无法判卷");
        }
        return testCases;
    }

    private void checkContestStatus(ContestEntity contestEntity) {
        if (contestEntity.getStatus() != ContestStatus.USING.getNumber()) {
            throw new WebErrorException("当前比赛不得参加");
        }
    }

    private void checkUserInContestValidation(ContestEntity contest, ContestUserEntity user) {
        // 如果比赛为限时模式，是否有权提交
        if (contest.getType() == ContestTypeStatus.OI_CONTEST_LIMIT_TIME.getNumber()
                || contest.getType() == ContestTypeStatus.ACM_CONTEST_LIMIT_TIME.getNumber()) {
            if (System.currentTimeMillis() > (user.getJoinTime()+contest.getTotalTime())) {
                throw new WebErrorException("你已超时，不能提交");
            }
        }
    }

    private void containLang(LanguageEnum lang, ProblemEntity problemEntity) {
        for (Object obj: problemEntity.getLang()) {
            if (lang == LanguageEnum.valueOf(obj.toString())) {
                return;
            }
        }
        throw new WebErrorException("不能使用此语言");
    }
}
