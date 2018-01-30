package com.eagleoj.web.service.async;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.entity.TestCaseRequestEntity;
import com.eagleoj.web.entity.TestCaseEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface AsyncJudgeService {
    String addTestJudge(String sourceCode, LanguageEnum lang, int time, int memory,
                        List<TestCaseEntity> testCases);

    String addProblemJudge(String sourceCode, LanguageEnum lang,
                           int owner, int pid);

    String addContestJudge(String sourceCode, LanguageEnum lang,
                           int owner, int pid,
                           int cid);

    String addGroupJudge(String sourceCode, LanguageEnum lang,
                         int owner, int pid,
                         int cid,
                         int gid);
}
