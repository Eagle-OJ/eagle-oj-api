package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.UserLogEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public class UserLogDao {
    public boolean updateLog(SqlSession sqlSession, UserLogEntity entity) {
        return sqlSession.update("userLog.updateLog", entity) == 1;
    }

    public List<UserLogEntity> getInWeek(SqlSession sqlSession, int uid) {
        return sqlSession.selectList("userLog.selectInWeek", uid);
    }

    public List<UserLogEntity> getInMonth(SqlSession sqlSession, int uid) {
        return sqlSession.selectList("userLog.selectInMonth", uid);
    }
}
