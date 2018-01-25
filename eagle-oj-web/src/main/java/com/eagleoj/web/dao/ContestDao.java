package com.eagleoj.web.dao;

import com.eagleoj.web.entity.ContestEntity;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.entity.ContestEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author = ygj
 **/
@Repository
public class ContestDao {

    public boolean addContest(SqlSession sqlSession,ContestEntity contestEntity){
        return sqlSession.insert("contest.addContest",contestEntity) == 1;
    }

    public List<Map<String, Object>> getValidContests(SqlSession sqlSession, PageRowBounds pager){
        return sqlSession.selectList("contest.getValidContests", null, pager);
    }

    public List<ContestEntity> getUserContests(SqlSession sqlSession, int uid, PageRowBounds pager) {
        return sqlSession.selectList("contest.getUserContests", uid, pager);
    }

    public boolean deleteContestByCid(SqlSession sqlSession,int cid){
        int deleteNum = sqlSession.delete("contest.deleteContestByCid",cid);
        return deleteNum == 1;
    }

    public boolean updateContestDescription(SqlSession sqlSession,ContestEntity contestEntity) {
        return sqlSession.update("contest.updateContestDescription",contestEntity) == 1;
    }

    public boolean updateContestStatus(SqlSession sqlSession, ContestEntity contestEntity) {
        return sqlSession.update("contest.updateContestStatus", contestEntity) == 1;
    }

    public ContestEntity getContestByCid(SqlSession sqlSession,int cid){
        return  sqlSession.selectOne("contest.getContestByCid",cid);
    }
}
