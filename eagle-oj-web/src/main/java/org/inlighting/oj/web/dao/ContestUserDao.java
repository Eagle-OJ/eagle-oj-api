package org.inlighting.oj.web.dao;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ContestUserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author = ygj
 **/
@Repository
public class ContestUserDao {

    public boolean add(SqlSession sqlSession, ContestUserEntity contestUserInfoEntity){
        return sqlSession.insert("contestUser.insert",contestUserInfoEntity) == 1;
    }

    public ContestUserEntity get(SqlSession sqlSession, ContestUserEntity entity) {
        return sqlSession.selectOne("contestUser.select", entity);
    }

    public List<Map<String, Object>> getNormalContestRankList(SqlSession sqlSession, int cid) {
        return sqlSession.selectList("contestUser.getNormalContestRankList", cid);
    }

    public List<Map<String, Object>> getACMContestRankList(SqlSession sqlSession, Map<String, Object> param) {
        return sqlSession.selectList("contestUser.getACMContestRankList", param);
    }

    public boolean updateTimesAndData(SqlSession sqlSession, ContestUserEntity entity) {
        return sqlSession.update("contestUser.updateTimesAndData", entity) == 1;
    }
}
