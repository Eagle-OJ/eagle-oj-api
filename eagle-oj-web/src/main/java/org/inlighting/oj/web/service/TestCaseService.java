package org.inlighting.oj.web.service;

import org.inlighting.oj.web.dao.TestCaseDao;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class TestCaseService {

    private TestCaseDao testCaseDao;

    @Autowired
    public void setTestCaseDao(TestCaseDao testCaseDao) {
        this.testCaseDao = testCaseDao;
    }

    public boolean addTestCase(int pid,
                               String stdin,
                               String stdout,
                               long create_time) {
        // todo
        return false;
    }

    public int getTestCaseCount(int pid) {
        // todo
        return 0;
    }

    public boolean updateTestCase(int pid, TestCaseEntity entity) {
        // todo
        return false;
    }

    public boolean deleteTestCase(int tid) {
        // todo
        return false;
    }
}
