package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.TagEntity;
import org.inlighting.oj.web.entity.TagProblemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public class TagsDao {
    public List<TagEntity> getTags(SqlSession sqlSession) {
        return sqlSession.selectList("tags.getAll");
    }

    public boolean addUsedTimes(SqlSession sqlSession, int tid) {
        return sqlSession.update("tags.addUsedTimes", tid) == 1;
    }

}
