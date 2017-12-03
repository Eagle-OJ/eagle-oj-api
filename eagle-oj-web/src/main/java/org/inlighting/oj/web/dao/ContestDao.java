package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ContestEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author = ygj
 **/
@Repository
public class ContestDao {

    public boolean addContest(SqlSession sqlSession,ContestEntity contestEntity){
        return sqlSession.insert("contest.addContest",contestEntity) == 1;
    }

    public List<ContestEntity> getAll(SqlSession sqlSession){
        return sqlSession.selectList("contest.getAll");
    }

    public boolean deleteContestByCid(SqlSession sqlSession,int cid){
        int deleteNum = sqlSession.delete("contest.deleteContestByCid",cid);
        return deleteNum == 1;
    }

    public boolean updateContestDescription(SqlSession sqlSession,ContestEntity contestEntity) {
        return sqlSession.update("contest.updateContestDescription",contestEntity) == 1;
    }

    public ContestEntity getContestByCid(SqlSession sqlSession,int cid){
        return  sqlSession.selectOne("contest.getContestByCid",cid);
    }
}
