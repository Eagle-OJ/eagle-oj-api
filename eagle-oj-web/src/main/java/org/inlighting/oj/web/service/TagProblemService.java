package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.TagProblemDao;
import org.inlighting.oj.web.entity.TagProblemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class TagProblemService {

    private TagProblemDao tagProblemDao;

    private SqlSession sqlSession;

    public TagProblemService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setTagProblemDao(TagProblemDao tagProblemDao) {
        this.tagProblemDao = tagProblemDao;
    }

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
