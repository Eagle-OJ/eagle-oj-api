package com.eagleoj.web.service;

import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.dao.TestCasesDao;
import com.eagleoj.web.entity.TestCaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
public interface TestCasesService {
    int save(int pid, String stdin, String stdout, int strength);

    int countProblemTestCases(int pid);

    List<TestCaseEntity> listProblemTestCases(int pid);

    boolean updateTestCaseByTidPid(int tid, int pid, String stdin, String stdout, int strength);

    boolean deleteTestCase(int tid);
}
