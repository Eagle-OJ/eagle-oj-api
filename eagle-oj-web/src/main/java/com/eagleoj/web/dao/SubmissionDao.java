package com.eagleoj.web.dao;

import com.eagleoj.web.entity.SubmissionEntity;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.entity.SubmissionEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Smith
 **/
@Repository
public class SubmissionDao {
    public boolean insert(SqlSession sqlSession, SubmissionEntity entity) {
        return sqlSession.insert("submission.insertSubmission", entity) == 1;
    }

    public List<HashMap<String, Object>> getSubmissions(SqlSession sqlSession, SubmissionEntity entity, PageRowBounds pager) {
        return sqlSession.selectList("submission.getSubmissions", entity, pager);
    }
}
