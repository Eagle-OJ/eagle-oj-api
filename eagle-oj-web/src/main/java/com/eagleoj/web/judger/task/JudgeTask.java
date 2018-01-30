package com.eagleoj.web.judger.task;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.web.entity.TestCaseEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface JudgeTask {
    String getId();

    String getSourceCode();

    LanguageEnum getLang();

    List<TestCaseEntity> getTestCases();

    int getTime();

    int getMemory();

    int getPriority();

}
