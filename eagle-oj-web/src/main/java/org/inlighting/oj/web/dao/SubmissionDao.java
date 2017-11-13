package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.SubmissionEntity;
import org.springframework.stereotype.Repository;

/**
 * @author Smith
 **/
@Repository
public class SubmissionDao {
    public boolean insert(SqlSession sqlSession, SubmissionEntity entity) {
        return sqlSession.insert("submission.insertSubmission", entity) == 1;
    }
}
