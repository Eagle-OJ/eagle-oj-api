package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.TestCaseDao;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class TestCaseService {

    private final SqlSession sqlSession;

    private TestCaseDao testCaseDao;

    public TestCaseService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setTestCaseDao(TestCaseDao testCaseDao) {
        this.testCaseDao = testCaseDao;
    }

    public int addTestCase(int pid, String stdin, String stdout, int strength) {
        // 添加一个TestCase
        TestCaseEntity testCaseEntity = new TestCaseEntity();
        testCaseEntity.setPid(pid);
        testCaseEntity.setStdin(stdin);
        testCaseEntity.setStdout(stdout);
        testCaseEntity.setStrength(strength);
        testCaseEntity.setCreateTime(System.currentTimeMillis());
        boolean flag = testCaseDao.addTestCase(sqlSession,testCaseEntity);
        return flag ? testCaseEntity.getTid() : 0;
    }

    public int getTestCaseCountByPid(int pid) {
        return testCaseDao.getTestCaseCountByPid(sqlSession,pid);
    }

    public List<TestCaseEntity> getAllTestCasesByPid(int pid){
        //通过pid来查询所有的TestCase
        return testCaseDao.getAllTestCasesByPid(sqlSession,pid);
    }

    public boolean updateTestCaseByTid(int tid,String stdin, String stdout, int strength) {
        //通过TestCase的ID来修改TestCase
        return testCaseDao.updateTestCaseByTid(sqlSession,new TestCaseEntity(tid,stdin,stdout,strength));
    }

    public boolean deleteTestCaseByTid(int tid) {
        //通过TestCase的ID删除TestCase
        return testCaseDao.deleteOneTestCaseByTid(sqlSession,tid);
    }
}
