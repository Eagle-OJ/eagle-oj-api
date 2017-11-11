package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public class TestCaseDao {

    public int getTestCaseCountByPid(SqlSession sqlsession,int pid){
        return sqlsession.selectOne("testCase.getTestCaseCountByPid",pid);
    }
    //添加一道题目的多个TestCase
    public boolean addTestCase(SqlSession sqlsession, TestCaseEntity entity) {
        int insertNum = sqlsession.insert("testCase.addTestCase", entity);
        return insertNum == 1;
    }

    public List<TestCaseEntity> getAllTestCasesByPid(SqlSession sqlsession, int pid) {
        return sqlsession.selectList("testCase.getAllTestCaseByPid", pid);
    }

    public boolean updateTestCaseByTid(SqlSession sqlsession,TestCaseEntity testCaseEntity) {
        int updateNum = sqlsession.update("testCase.updateTestCaseByTid", testCaseEntity);
        return updateNum == 1;
    }

    public boolean deleteOneTestCaseByTid(SqlSession sqlSession, int tid) {
        int deleteNum = sqlSession.delete("testCase.deleteOneTestCaseByTid", tid);
        return deleteNum == 1;
        //假如需要删除一道的题目，需要根据pid来删除所有的TestCase
    }
}
