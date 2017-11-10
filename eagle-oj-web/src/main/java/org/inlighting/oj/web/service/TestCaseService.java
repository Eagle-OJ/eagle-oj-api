package org.inlighting.oj.web.service;

import junit.framework.TestCase;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.TestCaseDao;
import org.inlighting.oj.web.data.DataHelper;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean addTestCase(int pid, String stdin, String stdout, int strength) {
        // 添加一个TestCase
        SqlSession sqlSession = DataHelper.getSession();
        TestCaseEntity testCaseEntity = new TestCaseEntity();
        testCaseEntity.setPid(pid);
        testCaseEntity.setStdin(stdin);
        testCaseEntity.setStdout(stdout);
        testCaseEntity.setStrength(strength);
        testCaseEntity.setCreateTime(System.currentTimeMillis());
        boolean flag = testCaseDao.addTestCase(sqlSession,testCaseEntity);
        sqlSession.close();
        return flag;
    }

    public int getTestCaseCountByPid(int pid) {
        SqlSession sqlSession = DataHelper.getSession();
        int updateNum = testCaseDao.getTestCaseCountByPid(sqlSession,pid);
        sqlSession.close();
        return updateNum ;
    }

    public List<TestCaseEntity> getAllTestCasesByPid(int pid){
        //通过pid来查询所有的TestCase
        SqlSession sqlSession = DataHelper.getSession();
        List<TestCaseEntity> list = (List<TestCaseEntity>) testCaseDao.getAllTestCasesByPid(sqlSession,pid);
        sqlSession.close();
        return list;
    }

    public boolean updateTestCaseByTid(int tid,String stdin, String stdout, int strength) {
        //通过TestCase的ID来修改TestCase
        SqlSession sqlSession = DataHelper.getSession();
        boolean flag = testCaseDao.updateTestCaseByTid(sqlSession,new TestCaseEntity(tid,stdin,stdout,strength));
        sqlSession.close();
        return flag;
    }

    public boolean deleteTestCaseByTid(int tid) {

        //通过TestCase的ID删除TestCase
        SqlSession sqlSession = DataHelper.getSession();
        boolean flag  = testCaseDao.deleteOneTestCaseByTid(sqlSession,tid);
        sqlSession.close();
        return flag;
    }
}
