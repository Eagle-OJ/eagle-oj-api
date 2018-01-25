package com.eagleoj.web.dao;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.entity.ProblemUserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public class ProblemUserDao {
    public boolean add(SqlSession sqlSession, ProblemUserEntity entity) {
        return sqlSession.insert("problemUser.insert", entity) == 1;
    }

    public boolean update(SqlSession sqlSession, ProblemUserEntity entity) {
        return sqlSession.update("problemUser.update", entity) == 1;
    }

    public ProblemUserEntity get(SqlSession sqlSession, ProblemUserEntity entity) {
        return sqlSession.selectOne("problemUser.select", entity);
    }

    public List<Map<String, Object>> getProblemUser(SqlSession sqlSession, int uid, PageRowBounds pager) {
        return sqlSession.selectList("problemUser.selectProblemUser", uid, pager);
    }

}
