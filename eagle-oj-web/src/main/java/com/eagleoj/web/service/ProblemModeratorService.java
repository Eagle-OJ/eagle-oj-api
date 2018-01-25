package com.eagleoj.web.service;

import com.eagleoj.web.entity.ProblemModeratorEntity;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.dao.ProblemModeratorDao;
import com.eagleoj.web.entity.ProblemModeratorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ProblemModeratorService {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private ProblemModeratorDao problemModeratorDao;

    public boolean add(int pid, int uid) {
        ProblemModeratorEntity entity = new ProblemModeratorEntity();
        entity.setUid(uid);
        entity.setPid(pid);
        return problemModeratorDao.add(sqlSession, entity);
    }

    public boolean isExist(int pid, int uid) {
        return get(pid, uid) != null;
    }

    public ProblemModeratorEntity get(int pid, int uid) {
        ProblemModeratorEntity entity = new ProblemModeratorEntity();
        entity.setPid(pid);
        entity.setUid(uid);
        return problemModeratorDao.get(sqlSession, entity);
    }

    public List<Map<String, Object>> getModerators(int pid) {
        return problemModeratorDao.getModerators(sqlSession, pid);
    }

    public boolean delete(int pid, int uid) {
        ProblemModeratorEntity entity = new ProblemModeratorEntity();
        entity.setPid(pid);
        entity.setUid(uid);
        return problemModeratorDao.delete(sqlSession, entity);
    }
}
