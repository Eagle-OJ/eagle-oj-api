package com.eagleoj.web.service;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.dao.TagsDao;
import com.eagleoj.web.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class TagsService {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private TagsDao tagsDao;

    public List<TagEntity> getTags() {
        return tagsDao.getTags(sqlSession);
    }

    public boolean addUsedTimes(int tid) {
        return tagsDao.addUsedTimes(sqlSession, tid);
    }
}
