package com.eagleoj.web.service;

import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.dao.UserLogDao;
import com.eagleoj.web.entity.UserLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class UserLogService {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private UserLogDao userLogDao;

    public boolean updateLog(int uid, UserLogEntity entity) {
        entity.setUid(uid);
        return userLogDao.updateLog(sqlSession, entity);
    }

    public List<UserLogEntity> getInWeek(int uid) {
        return userLogDao.getInWeek(sqlSession, uid);
    }

    public List<UserLogEntity> getInMonth(int uid) {
        return userLogDao.getInMonth(sqlSession, uid);
    }
}
