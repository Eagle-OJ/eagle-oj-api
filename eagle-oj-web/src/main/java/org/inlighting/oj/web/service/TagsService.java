package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.TagsDao;
import org.inlighting.oj.web.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class TagsService {

    private final SqlSession sqlSession;

    private TagsDao tagsDao;

    public TagsService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setTagsDao(TagsDao tagsDao) {
        this.tagsDao = tagsDao;
    }

    public List<TagEntity> getTags() {
        return tagsDao.getTags(sqlSession);
    }

    public boolean addUsedTimes(String name) {
        return tagsDao.addUsedTimes(sqlSession, name);
    }
}
