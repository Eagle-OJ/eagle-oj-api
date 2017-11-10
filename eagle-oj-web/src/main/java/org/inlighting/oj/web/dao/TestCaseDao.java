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
        int testCaseNum = sqlsession.selectOne("testCase.getTestCaseCountByPid",pid);
        return testCaseNum;
    }
    //添加一道题目的多个TestCase
    public boolean addTestCase(SqlSession sqlsession, TestCaseEntity entity) {
        int insertNum = sqlsession.insert("testCase.addTestCase", entity);
        if (insertNum == 1) {
            return true;
        } else {
            return false;
        }
    }

    public List<?> getAllTestCasesByPid(SqlSession sqlsession, int pid) {
        List<?> list = sqlsession.selectList("testCase.getAllTestCaseByPid", pid);
        return list;
    }

    public boolean updateTestCaseByTid(SqlSession sqlsession,TestCaseEntity testCaseEntity) {
        int updateNum = sqlsession.update("testCase.updateTestCaseByTid", testCaseEntity);
        if (updateNum == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteOneTestCaseByTid(SqlSession sqlSession, int tid) {
        int deleteNum = sqlSession.delete("testCase.deleteOneTestCaseByTid", tid);
        if (deleteNum == 1) {
            return true;
        } else {
            return false;

        }

        //假如需要删除一道的题目，需要根据pid来删除所有的TestCase
    }
}
