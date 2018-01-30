package com.eagleoj.web.service.impl.async;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.entity.TestCaseRequestEntity;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.data.status.ProblemStatus;
import com.eagleoj.web.entity.ProblemEntity;
import com.eagleoj.web.entity.TestCaseEntity;
import com.eagleoj.web.judger.JudgeQueue;
import com.eagleoj.web.judger.task.ContestJudgeTask;
import com.eagleoj.web.judger.task.GroupJudgeTask;
import com.eagleoj.web.judger.task.ProblemJudgeTask;
import com.eagleoj.web.judger.task.TestJudgeTask;
import com.eagleoj.web.service.ProblemService;
import com.eagleoj.web.service.TestCasesService;
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
        /*ContestJudgeTask task = new ContestJudgeTask();
        task.setId(generateId());
        task.setSourceCode(sourceCode);
        task.setLang(lang);
        task.setTime(time);
        task.setMemory(memory);
        task.setOwner(owner);
        task.setPid(pid);
        task.setCid(cid);
        task.setPriority(1);
        judgerQueue.addTask(task);
        return task.getId();*/
        return null;
    }

    @Override
    public String addGroupJudge(String sourceCode, LanguageEnum lang,
                                int owner, int pid,
                                int cid,
                                int gid) {
       /* GroupJudgeTask task = new GroupJudgeTask();
        task.setId(generateId());
        task.setSourceCode(sourceCode);
        task.setLang(lang);
        task.setTime(time);
        task.setMemory(memory);
        task.setOwner(owner);
        task.setPid(pid);
        task.setCid(cid);
        task.setGid(gid);
        task.setPriority(1);
        judgerQueue.addTask(task);
        return task.getId();*/
       return null;
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
}
