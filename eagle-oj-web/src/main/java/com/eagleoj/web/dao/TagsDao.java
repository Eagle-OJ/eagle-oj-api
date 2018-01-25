package com.eagleoj.web.dao;

import com.eagleoj.web.entity.TagEntity;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.entity.TagEntity;
import com.eagleoj.web.entity.TagProblemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public class TagsDao {
    public List<TagEntity> getTags(SqlSession sqlSession) {
        return sqlSession.selectList("tags.get");
    }

    public boolean addUsedTimes(SqlSession sqlSession, int tid) {
        return sqlSession.update("tags.addUsedTimes", tid) == 1;
    }

}
