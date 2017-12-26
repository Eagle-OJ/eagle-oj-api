package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.TagProblemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public class TagProblemDao {
    public boolean insertTagProblem(SqlSession sqlSession, TagProblemEntity entity) {
        return sqlSession.insert("tagProblem.insertTagProblem", entity) == 1;
    }

    public boolean deleteProblemTags(SqlSession sqlSession, int pid) {
        return sqlSession.delete("tagProblem.deleteProblemTags", pid) > 0;
    }

    public List<TagProblemEntity> getProblemTags(SqlSession sqlSession, int pid) {
        return sqlSession.selectList("tagProblem.getProblemTags", pid);
    }
}
