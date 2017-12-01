package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.TagEntity;
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

    public boolean addUsedTimes(SqlSession sqlSession, String name) {
        return sqlSession.update("tags.addUsedTimes", name) == 1;
    }
}
