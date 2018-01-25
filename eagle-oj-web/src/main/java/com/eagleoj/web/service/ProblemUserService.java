package com.eagleoj.web.service;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.dao.ProblemUserDao;
import com.eagleoj.web.entity.ProblemUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ProblemUserService {

    @Autowired
    private ProblemUserDao problemUserDao;

    @Autowired
    private SqlSession sqlSession;

    public boolean add(int pid, int uid, ResultEnum status) {
        ProblemUserEntity problemUserEntity = new ProblemUserEntity();
        problemUserEntity.setPid(pid);
        problemUserEntity.setUid(uid);
        problemUserEntity.setStatus(status);
        return problemUserDao.add(sqlSession, problemUserEntity);
    }

    public ProblemUserEntity get(int pid, int uid) {
        ProblemUserEntity problemUserEntity = new ProblemUserEntity();
        problemUserEntity.setPid(pid);
        problemUserEntity.setUid(uid);
        return problemUserDao.get(sqlSession, problemUserEntity);
    }

    public List<Map<String, Object>> getProblemUser(int uid, PageRowBounds pager) {
        return problemUserDao.getProblemUser(sqlSession, uid, pager);
    }

    public boolean update(int pid, int uid, ResultEnum result) {
        ProblemUserEntity problemUserEntity = new ProblemUserEntity();
        problemUserEntity.setPid(pid);
        problemUserEntity.setUid(uid);
        problemUserEntity.setStatus(result);
        return problemUserDao.update(sqlSession, problemUserEntity);
    }
}
