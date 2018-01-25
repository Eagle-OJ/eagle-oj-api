package com.eagleoj.web.service;

import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.dao.TagProblemDao;
import com.eagleoj.web.entity.TagProblemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class TagProblemService {

    @Autowired
    private TagProblemDao tagProblemDao;

    @Autowired
    private SqlSession sqlSession;

    public boolean addTagProblem(int tid, int pid) {
        TagProblemEntity entity = new TagProblemEntity();
        entity.setTid(tid);
        entity.setPid(pid);
        return tagProblemDao.insertTagProblem(sqlSession, entity);
    }

    public boolean deleteTagProblems(int pid) {
        return tagProblemDao.deleteProblemTags(sqlSession, pid);
    }

    public List<TagProblemEntity> getProblemTags(int pid) {
        return tagProblemDao.getProblemTags(sqlSession, pid);
    }
}
