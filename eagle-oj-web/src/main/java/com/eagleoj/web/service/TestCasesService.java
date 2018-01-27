package com.eagleoj.web.service;

import com.eagleoj.web.entity.TestCaseEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface TestCasesService {
    int save(int pid, String stdin, String stdout, int strength);

    int countProblemTestCases(int pid);

    List<TestCaseEntity> listProblemTestCases(int pid);

    void updateTestCaseByTidPid(int tid, int pid, String stdin, String stdout, int strength);

    void deleteTestCaseByTidPid(int tid, int pid);
}
